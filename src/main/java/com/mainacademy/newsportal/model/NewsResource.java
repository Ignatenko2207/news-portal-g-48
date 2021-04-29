package com.mainacademy.newsportal.model;

import com.mainacademy.newsportal.common.Language;
import com.mainacademy.newsportal.common.NewsCategory;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resources")
@Entity
public class NewsResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "api_id")
    private String apiId;
    private String name;
    @Column(name = "resource_url")
    private String resourceUrl;
    private Language language;
    private NewsCategory category;

}
