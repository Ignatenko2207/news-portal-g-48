package com.mainacademy.newsportal.api.client;

import com.mainacademy.newsportal.api.client.dto.NewsResponseDTO;
import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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
            @Value("${client.newsapi.properties.api-key:40f23b5268a54562ad05a0e112b4e433}")
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
        if (!StringUtils.isBlank(language)) {
            uriBuilder.queryParam("language", language);
        }
        uriBuilder.queryParam("apiKey", apiKey);
        ResourcesResponseDTO result = newsapiRestTemplate.getForEntity(uriBuilder.build(), ResourcesResponseDTO.class).getBody();
        if (nonNull(result) && result.getStatus().equals("ok")) {
            List<ResourcesResponseDTO.Resource> resources = result.getSources()
                    .stream()
                    .filter(it -> nonNull(it.getId()))
                    .collect(Collectors.toList());
            result.setSources(resources);
            return result;
        }
        throw new RuntimeException("Sources were not extracted!");
    }

    public NewsResponseDTO getTopNews(String country) {
        UriBuilder uriBuilder = getUriBuilder("/v2/top-headlines");
        if (!StringUtils.isBlank(country)) {
            uriBuilder.queryParam("country", country);
        }
        uriBuilder.queryParam("apiKey", apiKey);
        NewsResponseDTO result = newsapiRestTemplate.getForEntity(uriBuilder.build(), NewsResponseDTO.class).getBody();
        if (nonNull(result) && result.getStatus().equals("ok")) {
            List<NewsResponseDTO.Article> articles = result.getArticles()
                    .stream()
                    .filter(it -> nonNull(it.getSource().getId()))
                    .collect(Collectors.toList());
            result.setArticles(articles);
            return result;
        }
        throw new RuntimeException("Sources were not extracted!");
    }

    public NewsResponseDTO getOtherNews(String language) {
        UriBuilder uriBuilder = getUriBuilder("/v2/everything");
        if (!StringUtils.isBlank(language)) {
            uriBuilder.queryParam("language", language);
        }
        uriBuilder.queryParam("apiKey", apiKey);
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
        throw new RuntimeException("Sources were not extracted!");
    }

    private UriBuilder getUriBuilder(String url) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        return factory.builder().path(url);
    }

}
