package com.mainacademy.newsportal.service.impl;

import com.mainacademy.newsportal.common.NewsCategory;
import com.mainacademy.newsportal.dao.NewsContentRepository;
import com.mainacademy.newsportal.model.NewsContent;
import com.mainacademy.newsportal.model.NewsResource;
import com.mainacademy.newsportal.service.NewsContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsContentServiceImpl implements NewsContentService {

    private final NewsContentRepository newsContentRepository;

    @Override
    public void saveAll(List<NewsContent> contentList) {
        List<NewsContent> savedContent = newsContentRepository.findAll();
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
        List<NewsContent> savedContent = newsContentRepository.findAll();
        if (!savedContent.
                stream()
                .map(NewsContent::getNewsUrl)
                .collect(Collectors.toList())
                .contains(content.getNewsUrl())) {
            newsContentRepository.save(content);
        }
    }

    @Override
    public List<NewsContent> findAll() {
        return newsContentRepository.findAll();
    }

    @Override
    public List<NewsContent> findByCategory(String category) {
        return newsContentRepository.findAllByCategory(NewsCategory.ofName(category));
    }

    @Override
    public List<NewsContent> findByText(String fragment, boolean searchInTitle) {
        List<NewsContent> result = newsContentRepository.findAllByContentContains(fragment);
        if (searchInTitle) {
            result.addAll(newsContentRepository.findAllByTitleContains(fragment));
        }
        return result;
    }

    @Override
    public void deleteContentBefore(LocalDateTime time) {
        newsContentRepository.deleteAllByPublishedTimeBefore(time);
    }
}
