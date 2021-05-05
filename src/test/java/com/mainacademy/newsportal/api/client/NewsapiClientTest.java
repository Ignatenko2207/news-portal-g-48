package com.mainacademy.newsportal.api.client;

import com.mainacademy.newsportal.api.client.dto.NewsResponseDTO;
import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import org.apache.el.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class NewsapiClientTest {

    @Autowired
    NewsapiClient newsapiClient;

    @Test
    void getAcceptableResources() {
        ResourcesResponseDTO result = newsapiClient.getAcceptableResources("ru");

        Set<String> categories = result.getSources()
        .stream()
        .map(ResourcesResponseDTO.Resource::getCategory)
        .collect(Collectors.toSet());

        categories.forEach(System.out::println);
        result.getSources().forEach(System.out::println);
    }

    @Test
    void getTopNews() {
        NewsResponseDTO resultRu = newsapiClient.getTopNews("ru");

        System.out.println("The total number of results available for your request language RU: " + resultRu.getTotalResults());
        System.out.println(resultRu.getArticles().get(0).getPublishedAt());

        Set<String> titles = resultRu.getArticles()
                .stream()
                .map(NewsResponseDTO.Article::getTitle)
                .collect(Collectors.toSet());
        titles.forEach(System.out::println);

        System.out.println(resultRu);
    }

    @Test
    void getOtherNews() {
        ResourcesResponseDTO.Resource resource = ResourcesResponseDTO.Resource.builder()
                .id("lenta")
                .name("Lenta")
                .url("https://lenta.ru")
                .category("general")
                .language("ru")
                .country("ru")
                .build();

        NewsResponseDTO resultLenta = newsapiClient.getOtherNews(resource);
        System.out.println("The total number of results available for your request resource 'Lenta': " + resultLenta.getTotalResults());
    }
}