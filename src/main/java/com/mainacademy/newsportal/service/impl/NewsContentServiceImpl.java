package com.mainacademy.newsportal.service.impl;

import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.dao.NewsContentRepository;
import com.mainacademy.newsportal.model.NewsContent;
import com.mainacademy.newsportal.service.NewsContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsContentServiceImpl implements NewsContentService {

    private static final Sort DEFAULT_SORT = Sort.by("published_time").descending();
    private final NewsContentRepository newsContentRepository;

    @Override
    public void saveAll(List<NewsContent> contentList) {
        List<NewsContent> savedContent = (List<NewsContent>) newsContentRepository.findAll();
        newsContentRepository.saveAll(
                contentList
                        .stream()
                        .filter( it ->
                                !savedContent
                                        .stream()
                                        .map(NewsContent::getNewsUrl)
                                        .collect(Collectors.toList())
                                        .contains(it.getNewsUrl())
                        )
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void save(NewsContent content) {
        List<NewsContent> savedContent = (List<NewsContent>) newsContentRepository.findAll();
        if (!savedContent.
                stream()
                .map(NewsContent::getNewsUrl)
                .collect(Collectors.toList())
                .contains(content.getNewsUrl())) {
            newsContentRepository.save(content);
        }
    }

    @Override
    public Page<NewsContent> findByCategory(String category, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
        return newsContentRepository.findAllByCategory(NewsCategory.ofName(category), pageable);
    }

    @Override
    public Page<NewsContent> findByText(String fragment, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
        return newsContentRepository.findAllByContentContains(fragment, pageable);
    }

    @Override
    public void deleteContentBefore(LocalDateTime time) {
        newsContentRepository.deleteAllByPublishedTimeBefore(time);
    }

    @Override
    public NewsContent findById(Integer id) {
        return newsContentRepository.findById(id).orElse(null);
    }

}
