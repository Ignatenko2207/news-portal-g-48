package com.mainacademy.newsportal.service.impl;

import com.mainacademy.newsportal.dao.NewsResourceRepository;
import com.mainacademy.newsportal.model.NewsResource;
import com.mainacademy.newsportal.service.NewsResourcesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsResourcesServiceImpl implements NewsResourcesService {

    private final NewsResourceRepository newsResourceRepository;

    @Override
    public void saveAll(List<NewsResource> resources) {
        List<NewsResource> savedResources = newsResourceRepository.findAll();
        newsResourceRepository.saveAll(
                resources
                        .stream()
                        .filter( it ->
                                !savedResources
                                        .stream()
                                        .map(NewsResource::getApiId)
                                        .collect(Collectors.toList())
                                        .contains(it.getApiId())
                        )
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void save(NewsResource resource) {
        List<NewsResource> savedResources = newsResourceRepository.findAll();
        if (!savedResources.
                stream()
                .map(NewsResource::getApiId)
                .collect(Collectors.toList())
                .contains(resource.getApiId())) {
            newsResourceRepository.save(resource);
        }
    }

    @Override
    public List<NewsResource> findAll() {
        return newsResourceRepository.findAll();
    }

}
