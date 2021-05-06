package com.mainacademy.newsportal.dao;

import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.model.NewsContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsContentRepository extends PagingAndSortingRepository<NewsContent, Integer> {

//    List<NewsContent> findAll();
    Page<NewsContent> findAllByCategory(NewsCategory category, Pageable pageable);
    Page<NewsContent> findAllByTitleContains(String fragment, Pageable pageable);
    Page<NewsContent> findAllByContentContains(String fragment, Pageable pageable);
    void deleteAllByPublishedTimeBefore(LocalDateTime time);

}
