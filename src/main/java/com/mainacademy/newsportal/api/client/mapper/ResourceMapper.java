package com.mainacademy.newsportal.api.client.mapper;

import com.mainacademy.newsportal.api.client.dto.ResourcesResponseDTO;
import com.mainacademy.newsportal.model.NewsResource;

public interface ResourceMapper {

   NewsResource toModel(ResourcesResponseDTO.Resource resource);

}
