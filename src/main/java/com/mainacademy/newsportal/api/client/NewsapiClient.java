package com.mainacademy.newsportal.api.client;

import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;

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

    public ResourcesResponseDTO getAcceptableResourses() {
        String resourcesUrl = "/v2/sources";
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(baseUrl);
        URI uri = factory.builder().path(resourcesUrl).queryParam("apiKey", apiKey).build();
        return newsapiRestTemplate.getForEntity(uri, ResourcesResponseDTO.class).getBody();
    }

}
