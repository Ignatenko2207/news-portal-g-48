package com.mainacademy.newsportal.api.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsResponseDTO {

    private String status;
    private Integer totalResults;

    private List<Article> articles;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Article {

        private Source source;
        private String author;
        private String title;
        private String description;
        private String url;
        private String urlToImage;
        private String content;

        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        private LocalDateTime publishedAt;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Source {

        private String id;
        private String name;

    }

}
