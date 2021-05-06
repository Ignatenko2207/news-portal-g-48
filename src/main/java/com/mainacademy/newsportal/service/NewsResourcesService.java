package com.mainacademy.newsportal.service;

import com.mainacademy.newsportal.model.NewsResource;

import java.util.List;

public interface NewsResourcesService {

    void saveAll(List<NewsResource> resources);
    void save(NewsResource resource);
    List<NewsResource> findAll();

}
