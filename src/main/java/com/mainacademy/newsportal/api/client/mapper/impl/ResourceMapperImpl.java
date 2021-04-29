package com.mainacademy.newsportal.api.client.mapper.impl;

import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import com.mainacademy.newsportal.api.client.mapper.ResourceMapper;
import com.mainacademy.newsportal.common.Language;
import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.model.NewsResource;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapperImpl implements ResourceMapper {

    @Override
    public NewsResource toModel(ResourcesResponseDTO.Resource resource) {
        return NewsResource.builder()
                .apiId(resource.getId())
                .name(resource.getName())
                .resourceUrl(resource.getUrl())
                .language(Language.of(resource.getLanguage()))
                .category(NewsCategory.ofName(resource.getCategory()))
                .build();
    }
}
