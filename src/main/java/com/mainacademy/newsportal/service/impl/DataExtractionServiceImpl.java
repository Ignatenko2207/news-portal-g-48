package com.mainacademy.newsportal.service.impl;

import com.mainacademy.newsportal.api.client.NewsapiClient;
import com.mainacademy.newsportal.api.client.dto.NewsResponseDTO;
import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import com.mainacademy.newsportal.api.client.mapper.ResourceMapper;
import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.dao.NewsResourceRepository;
import com.mainacademy.newsportal.model.NewsContent;
import com.mainacademy.newsportal.model.NewsResource;
import com.mainacademy.newsportal.service.DataExtractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataExtractionServiceImpl implements DataExtractionService {

    private final NewsapiClient newsapiClient;
    private final ResourceMapper resourceMapper;
    private final NewsResourceRepository newsResourceRepository;

    @Override
    public List<NewsContent> extractNews() {
        // extract top news
        List<NewsResponseDTO.Article> topArticles = newsapiClient.getTopNews("en").getArticles();
        topArticles.addAll(newsapiClient.getTopNews("ru").getArticles());
        List<NewsContent> result = new ArrayList<>(convertToNewsModel(topArticles, NewsCategory.TOP));
        List<String> resources = newsResourceRepository.findAll()
                .stream()
                .map(NewsResource::getApiId)
                .collect(Collectors.toList());
        // extract news
        List<NewsResponseDTO.Article> articles = new ArrayList<>();
        for (String resourceId : resources) {
            articles.addAll(newsapiClient.getOtherNews(resourceId).getArticles());
        }
        result.addAll(convertToNewsModel(topArticles, null));
        return result;
    }

    @Override
    public List<NewsResource> extractResources() {
        List<ResourcesResponseDTO.Resource> resources = newsapiClient.getAcceptableResourses("en").getSources();
        resources.addAll(newsapiClient.getAcceptableResourses("ru").getSources());
        return resources.stream()
                .map(resourceMapper::toModel)
                .filter(it -> nonNull(it.getApiId()))
                .collect(Collectors.toList());
    }

    private List<NewsContent> convertToNewsModel(List<NewsResponseDTO.Article> articles, NewsCategory newsCategory) {
        Map<String, NewsResource> resourcesMap = newsResourceRepository.findAll()
                .stream()
                .collect(Collectors.toMap(NewsResource::getApiId, it -> it));
        List<String> resourseIds = resourcesMap.values()
                .stream()
                .map(NewsResource::getApiId)
                .collect(Collectors.toList());

        return articles
                .stream()
                .filter(article -> nonNull(article.getSource().getId()) &&
                        resourseIds.contains(article.getSource().getId()))
                .map(article -> {
                    NewsResource resource = resourcesMap.get(article.getSource().getId());
                    return NewsContent.builder()
                            .resource(resource)
                            .author(article.getAuthor())
                            .title(article.getTitle())
                            .description(article.getDescription())
                            .newsUrl(article.getUrl())
                            .imageUrl(article.getUrlToImage())
                            .publishedTime(article.getPublishedAt())
                            .language(resource.getLanguage())
                            .content(article.getContent())
                            .category(nonNull(newsCategory) ? newsCategory : resource.getCategory())
                            .build();
                })
                .collect(Collectors.toList()
                );
    }

}
