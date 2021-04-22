package com.mainacademy.newsportal.model;

import com.mainacademy.newsportal.common.Language;
import com.mainacademy.newsportal.common.LocalizedProperty;
import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.common.NewsContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsContent {

    private Integer id;

    private NewsResource resourceName;
    private String author;
    private String title;
    private String description;
    private String newsUrl;
    private String imageUrl;
    private LocalDateTime publishedTime;
    private Language language;
    private String content;
    private NewsCategory newsCategory;

}
