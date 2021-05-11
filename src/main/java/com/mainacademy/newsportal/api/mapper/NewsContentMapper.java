package com.mainacademy.newsportal.api.mapper;

import com.mainacademy.newsportal.api.dto.NewsContentResponseDTO;
import com.mainacademy.newsportal.model.NewsContent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Component
public class NewsContentMapper {

    private static final Locale DEFAULT_LOCALE = Locale.forLanguageTag("ru");

    public NewsContentResponseDTO.NewsContentDTO toDTO(NewsContent newsContent, Locale locale) {
        if (isNull(newsContent)) {
            return null;
        }
        if (isNull(locale)) {
            locale = DEFAULT_LOCALE;
        }
        return NewsContentResponseDTO.NewsContentDTO.builder()
                .id(newsContent.getId())
                .resourceName(newsContent.getResource().getName())
                .resourceUrl(newsContent.getResource().getResourceUrl())
                .author(newsContent.getAuthor())
                .title(newsContent.getTitle())
                .description(newsContent.getDescription())
                .newsUrl(newsContent.getNewsUrl())
                .imageUrl(newsContent.getImageUrl())
                .publishedTime(newsContent.getPublishedTime())
                .content(newsContent.getContent())
                .category(newsContent.getCategory().getLocalizedProperty().getByLocale(locale))
                .build();
    }

    public List<NewsContentResponseDTO.NewsContentDTO> toListDTO(List<NewsContent> newsContentList, Locale locale) {
        return newsContentList.stream()
                .map(newsContent -> toDTO(newsContent, locale))
                .collect(Collectors.toList());
    }
}
