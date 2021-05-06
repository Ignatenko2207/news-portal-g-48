package com.mainacademy.newsportal.api.client;

import com.mainacademy.newsportal.api.client.dto.NewsResponseDTO;
import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class NewsapiClient {

    private static final String DEFAULT_LANGUAGE = "en";
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

    public ResourcesResponseDTO getAcceptableResourses(String language) {
        URIBuilder uriBuilder = getUriBuilder("/v2/sources");
        if (StringUtils.isBlank(language)) {
            language = DEFAULT_LANGUAGE;
        }
        uriBuilder.addParameter("language", language);
        ResourcesResponseDTO result = null;
        try {
            result = newsapiRestTemplate.getForEntity(uriBuilder.build(), ResourcesResponseDTO.class).getBody();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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

    public NewsResponseDTO getTopNews(String language) {
        URIBuilder uriBuilder = getUriBuilder("/v2/top-headlines");
        if (StringUtils.isBlank(language)) {
            language = DEFAULT_LANGUAGE;
        }
        uriBuilder.addParameter("language", language);
        NewsResponseDTO result = null;
        try {
            result = newsapiRestTemplate.getForEntity(uriBuilder.build(), NewsResponseDTO.class).getBody();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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

    public NewsResponseDTO getOtherNews(String sourceId) {
        URIBuilder uriBuilder = getUriBuilder("/v2/everything");
        if (StringUtils.isNotBlank(sourceId)) {
            uriBuilder.addParameter("sources", sourceId);
        }
        NewsResponseDTO result = null;
        try {
            result = newsapiRestTemplate.getForEntity(uriBuilder.build(), NewsResponseDTO.class).getBody();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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

    private URIBuilder getUriBuilder(String url) {
        try {
            URIBuilder builder = new URIBuilder(baseUrl + url);
            builder.addParameter("apiKey", apiKey);
            return builder;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Illegal base URL to create URI " + baseUrl + url);
    }

}
