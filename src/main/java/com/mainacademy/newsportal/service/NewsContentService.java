package com.mainacademy.newsportal.service;

import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.model.NewsContent;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsContentService {

    void saveAll(List<NewsContent> contentList);
    void save(NewsContent content);
    List<NewsContent> findAll();
    List<NewsContent> findByCategory(String category);
    List<NewsContent> findByText(String fragment, boolean searchInTitle);
    void deleteContentBefore(LocalDateTime time);

}
