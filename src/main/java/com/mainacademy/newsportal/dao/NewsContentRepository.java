package com.mainacademy.newsportal.dao;

import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.model.NewsContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsContentRepository extends JpaRepository<NewsContent, Integer> {

    List<NewsContent> findAllByCategory(NewsCategory category);
    List<NewsContent> findAllByTitleContains(String fragment);
    List<NewsContent> findAllByContentContains(String fragment);
    void deleteAllByPublishedTimeBefore(LocalDateTime time);

}
