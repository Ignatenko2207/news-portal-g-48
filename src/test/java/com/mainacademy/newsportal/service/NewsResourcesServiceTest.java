package com.mainacademy.newsportal.service;

import com.mainacademy.newsportal.model.NewsResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class NewsResourcesServiceTest {

    @Autowired
    NewsResourcesService newsResourcesService;
    @Autowired
    DataExtractionService dataExtractionService;

    @Test
    void saveAll() {
        List<NewsResource> resources = dataExtractionService.extractResources();
        newsResourcesService.saveAll(resources);
    }

    @Test
    void save() {
        List<NewsResource> resources = dataExtractionService.extractResources();
        newsResourcesService.save(resources.get(0)); //before delete first resource in database
    }
}