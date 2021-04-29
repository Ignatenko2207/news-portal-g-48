package com.mainacademy.newsportal.api.client;

import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles("test")
class NewsapiClientTest {

    @Autowired
    NewsapiClient newsapiClient;

    @Test
    void getAcceptableResourses() {
        ResourcesResponseDTO result = newsapiClient.getAcceptableResourses("ru");
        Set<String> categories = result.getSources()
        .stream()
        .map(ResourcesResponseDTO.Resource::getCategory)
        .collect(Collectors.toSet());

        categories.forEach(System.out::println);
    }
}