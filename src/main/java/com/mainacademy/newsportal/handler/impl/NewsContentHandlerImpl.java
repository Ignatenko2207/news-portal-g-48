package com.mainacademy.newsportal.handler.impl;

import com.mainacademy.newsportal.api.dto.NewsContentResponseDTO;
import com.mainacademy.newsportal.api.mapper.NewsContentMapper;
import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.handler.NewsContentHandler;
import com.mainacademy.newsportal.model.NewsContent;
import com.mainacademy.newsportal.service.NewsContentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class NewsContentHandlerImpl implements NewsContentHandler {

    private final NewsContentService newsContentService;
    private final NewsContentMapper newsContentMapper;

    @Override
    public NewsContentResponseDTO agregateNewsContent(Locale locale) {
        int page = 1;
        int size = 5;
        List<String> newsCategoryIds =
                Arrays.stream(NewsCategory.values())
                        .map(NewsCategory::getId)
                        .collect(Collectors.toList());
        return NewsContentResponseDTO.builder()
                .data(collectData(newsCategoryIds, locale, page, size))
                .build();
    }

    @Override
    public NewsContentResponseDTO getNewsByCategory(String category, Integer page, Integer size, Locale locale) {
        return NewsContentResponseDTO.builder()
                .data(collectData(List.of(category), locale, page, size))
                .build();
    }

    @Override
    public NewsContentResponseDTO.NewsContentDTO getNewsById(Integer id, Locale locale) {
        return newsContentMapper.toDTO(newsContentService.findById(id), locale);
    }

    private Map<NewsContentResponseDTO.NewsCategoryDTO, Page<NewsContentResponseDTO.NewsContentDTO>> collectData(List<String> newsCategoryIds, Locale locale, int page, int size) {
        Map<NewsContentResponseDTO.NewsCategoryDTO, Page<NewsContentResponseDTO.NewsContentDTO>> data = new HashMap<>();
        newsCategoryIds.forEach(category ->
                data.put(
                        NewsContentResponseDTO.NewsCategoryDTO
                                .builder()
                                .id(category)
                                .name(NewsCategory.ofName(category).getLocalizedProperty().getByLocale(locale))
                                .build(),
                        new PageImpl<>(newsContentMapper.toListDTO(
                                newsContentService.findByCategory(category, page, size).toList()
                                , locale))
                )
        );
        return data;
    }

}
