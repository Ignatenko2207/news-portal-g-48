package com.mainacademy.newsportal.api.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsContentResponseDTO {

    private Map<NewsCategoryDTO, Page<NewsContentDTO>> data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class NewsCategoryDTO {
        private String id;
        private String name;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NewsContentDTO {
        private Integer id;
        private String resourceName;
        private String resourceUrl;
        private String author;
        private String title;
        private String description;
        private String newsUrl;
        private String imageUrl;
        private LocalDateTime publishedTime;
        private String content;
        private String category;
    }
}
