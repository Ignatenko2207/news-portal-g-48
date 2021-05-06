package com.mainacademy.newsportal.model;

import com.mainacademy.newsportal.common.Language;
import com.mainacademy.newsportal.common.NewsCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "content")
@Entity
public class NewsContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = NewsResource.class, cascade = CascadeType.ALL)
    private NewsResource resource;
    private String author;
    private String title;
    private String description;
    @Column(name = "news_url")
    private String newsUrl;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "published_time")
    private LocalDateTime publishedTime;
    private Language language;
    private String content;
    private NewsCategory category;

}
