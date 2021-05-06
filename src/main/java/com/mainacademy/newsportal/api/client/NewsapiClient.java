package com.mainacademy.newsportal.api.client;

import com.mainacademy.newsportal.api.client.dto.NewsResponseDTO;
import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import com.mainacademy.newsportal.model.NewsResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class NewsapiClient {

    private final String apiKey;
    private final String baseUrl;
    private final RestTemplate newsapiRestTemplate;

    public NewsapiClient(
            @Value("${client.newsapi.properties.api-key:f1c821175ea64a5c82b4f17151925c25}")
                    String apiKey,
            @Value("${client.newsapi.properties.base-url:https://newsapi.org}")
                    String baseUrl,
            RestTemplate newsapiRestTemplate) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.newsapiRestTemplate = newsapiRestTemplate;
    }

    public ResourcesResponseDTO getAcceptableResources(String language) {
        UriBuilder uriBuilder = getUriBuilder("/v2/sources");
        uriBuilder.queryParam("language", checkLanguage(language));
        ResourcesResponseDTO result = newsapiRestTemplate.getForEntity(uriBuilder.build(), ResourcesResponseDTO.class).getBody();
        if (nonNull(result) && result.getStatus().equals("ok")) {
            List<ResourcesResponseDTO.Resource> resources =
                    result.getSources()
                            .stream()
                            .filter(it -> nonNull(it.getId()))
                            .collect(Collectors.toList());
            result.setSources(resources);
            return result;
        }
        throw new RuntimeException("Sources were not extracted!");
    }

    public NewsResponseDTO getTopNews(String language) {
        UriBuilder uriBuilder = getUriBuilder("/v2/top-headlines");
        uriBuilder.queryParam("language", checkLanguage(language));
        NewsResponseDTO result = newsapiRestTemplate.getForEntity(uriBuilder.build(), NewsResponseDTO.class).getBody();
        if (nonNull(result) && result.getStatus().equals("ok")) {
            List<NewsResponseDTO.Article> articles =
                    result.getArticles()
                            .stream()
                            .filter(it -> nonNull(it.getSource().getId()))
                            .collect(Collectors.toList());
            result.setArticles(articles);
            return result;
        }
        throw new RuntimeException("Top news were not extracted!");
    }

    public NewsResponseDTO getOtherNews(NewsResource resources) {
        UriBuilder uriBuilder = getUriBuilder("/v2/everything");
        uriBuilder.queryParam("sources", resources.getApiId());
        uriBuilder.queryParam("sortBy", "publishedAt");
        NewsResponseDTO result = newsapiRestTemplate.getForEntity(uriBuilder.build(), NewsResponseDTO.class).getBody();
        if (nonNull(result) && result.getStatus().equals("ok")) {
            List<NewsResponseDTO.Article> articles =
                    result.getArticles()
                            .stream()
                            .filter(it -> nonNull(it.getSource().getId()))
                            .collect(Collectors.toList());
            result.setArticles(articles);
            return result;
        }
        throw new RuntimeException("Other news were not extracted!");
    }

    private UriBuilder getUriBuilder(String url) {
        UriBuilder uriBuilder = new DefaultUriBuilderFactory(baseUrl).builder().path(url);
        uriBuilder.queryParam("apiKey", apiKey);
        return uriBuilder;
    }

    private String checkLanguage(String language) {
        if (StringUtils.equals(language, "ru"))
            return "ru";
        return "en";
    }

}
