package com.mainacademy.newsportal.handler;

import com.mainacademy.newsportal.api.dto.NewsContentResponseDTO;

import java.util.Locale;

public interface NewsContentHandler {

    NewsContentResponseDTO agregateNewsContent(Locale locale);

    NewsContentResponseDTO getNewsByCategory(String category, Integer page, Integer size, Locale locale);

    NewsContentResponseDTO.NewsContentDTO getNewsById(Integer id, Locale locale);
}
