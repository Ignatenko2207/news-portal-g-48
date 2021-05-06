package com.mainacademy.newsportal.api.client;

import com.mainacademy.newsportal.api.client.dto.NewsResponseDTO;
import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import com.mainacademy.newsportal.common.Language;
import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.dao.NewsResourceRepository;
import com.mainacademy.newsportal.model.NewsResource;
import org.apache.el.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class NewsapiClientTest {

    @Autowired
    NewsapiClient newsapiClient;
    @Autowired
    NewsResourceRepository newsResourceRepository;

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
        NewsResponseDTO resultEn = newsapiClient.getTopNews("en");

        System.out.println("The total size of results available for your request language RU: " + resultRu.getArticles().size());
        System.out.println("The total size of results available for your request language EN: " + resultEn.getArticles().size());
        System.out.println(resultEn.getArticles().get(0).getPublishedAt());

        System.out.println(resultEn);
    }

    @Test
    void getOtherNews() {
        NewsResource resource = newsResourceRepository.findAll().get(0);
        NewsResponseDTO result = newsapiClient.getOtherNews(resource);
        System.out.println("The total number of results available for your request : " + result.getTotalResults());
    }
}