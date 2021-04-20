package com.mainacademy.newsportal.model;

import com.mainacademy.newsportal.common.LocalizedProperty;
import com.mainacademy.newsportal.common.NewsContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsContent {

    private Integer id;
    private Integer newsId;
    private int orderNo;
    private NewsContentType newsContentType;
    private String mediaLink;
    private LocalizedProperty content;

}
