package com.mainacademy.newsportal.service;

import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import com.mainacademy.newsportal.model.NewsContent;
import com.mainacademy.newsportal.model.NewsResource;

import java.util.List;

public interface DataExtractionService {

    List<NewsContent> extractTopNews();
    List<NewsContent> extractOtherNews(ResourcesResponseDTO.Resource resource);
    List<NewsResource> extractResources();

}
