package com.mainacademy.newsportal.service;

import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import com.mainacademy.newsportal.model.NewsContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class NewsContentServiceTest {

    @Autowired
    NewsContentService newsContentService;
    @Autowired
    DataExtractionService dataExtractionService;

    @Test
    void saveAll() {
    }

    @Test
    void save() {
        List<NewsContent> newsContent = dataExtractionService.extractTopNews();
        newsContentService.save(newsContent.get(2));
    }

    @Test
    void findAll() {
    }

    @Test
    void findByCategory() {
    }

    @Test
    void findByText() {
    }

    @Test
    void deleteContentBefore() {
    }
}