package com.mainacademy.newsportal.service;

import com.mainacademy.newsportal.model.NewsContent;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsContentService {

    void saveAll(List<NewsContent> contentList);
    void save(NewsContent content);
    Page<NewsContent> findByCategory(String category, Integer page, Integer size);
    Page<NewsContent> findByText(String fragment, Integer page, Integer size);
    void deleteContentBefore(LocalDateTime time);
    NewsContent findById(Integer id);
}
