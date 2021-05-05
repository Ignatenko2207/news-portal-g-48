package com.mainacademy.newsportal.service.impl;

import com.mainacademy.newsportal.api.client.NewsapiClient;
import com.mainacademy.newsportal.api.client.dto.NewsResponseDTO;
import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import com.mainacademy.newsportal.api.client.mapper.ResourceMapper;
import com.mainacademy.newsportal.common.Language;
import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.model.NewsContent;
import com.mainacademy.newsportal.model.NewsResource;
import com.mainacademy.newsportal.service.DataExtractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataExtractionServiceImpl implements DataExtractionService {

    private final NewsapiClient newsapiClient;
    private final ResourceMapper resourceMapper;

    @Override
    public List<NewsContent> extractTopNews() {
        List<NewsResponseDTO.Article> topArticles = newsapiClient.getTopNews("en").getArticles();
        topArticles.addAll(newsapiClient.getTopNews("ru").getArticles());
        return convertNewsToModel(topArticles);
    }

    @Override
    public List<NewsContent> extractOtherNews(ResourcesResponseDTO.Resource resource) {
        List<NewsResponseDTO.Article> otherArticles = newsapiClient.getOtherNews(resource).getArticles();
        return convertNewsToModel(otherArticles);
    }

    @Override
    public List<NewsResource> extractResources() {
        List<ResourcesResponseDTO.Resource> resources = newsapiClient.getAcceptableResources("en").getSources();
        resources.addAll(newsapiClient.getAcceptableResources("ru").getSources());
        return resources.stream()
                .map(resourceMapper::toModel)
                .collect(Collectors.toList());
    }

    private List<NewsContent> convertNewsToModel(List<NewsResponseDTO.Article> articles) {
        Map<String, NewsResource> resourcesMap = extractResources()
                .stream()
                .collect(Collectors.toMap(NewsResource::getApiId, it -> it));

        List<NewsContent> result = articles
                .stream()
                .map(article -> {
                    NewsResource resource = resourcesMap.get(article.getSource().getId());
                    return NewsContent.builder()
                            .resource(resource) //TODO: fix error
                            .author(article.getAuthor())
                            .title(article.getTitle())
                            .description(article.getDescription())
                            .newsUrl(article.getUrl())
                            .imageUrl(article.getUrlToImage())
                            .publishedTime(article.getPublishedAt())
//                            .language(resource.getLanguage()) //TODO: fix error
                            .language(Language.EN)
                            .content(article.getContent())
                            .category(NewsCategory.TOP) // check
                            .build();
                })
                .collect(Collectors.toList());
        return result;
    }
}
