package com.mainacademy.newsportal.model;

import com.mainacademy.newsportal.common.Language;
import com.mainacademy.newsportal.common.NewsCategory;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResource {

    private Integer id;
    private String apiId;
    private String name;
    private String resourceUrl;
    private Language language;
    private NewsCategory category;

}
