package com.mainacademy.newsportal.processor.impl;

import com.mainacademy.newsportal.model.NewsContent;
import com.mainacademy.newsportal.model.NewsResource;
import com.mainacademy.newsportal.processor.SchedulingProcessor;
import com.mainacademy.newsportal.service.DataExtractionService;
import com.mainacademy.newsportal.service.NewsContentService;
import com.mainacademy.newsportal.service.NewsResourcesService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SchedulingProcessorImpl implements SchedulingProcessor {

    private static final long DEFAULT_PERIOD = 3; // three days
    private final DataExtractionService dataExtractionService;
    private final NewsResourcesService newsResourcesService;
    private final NewsContentService newsContentService;

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void extractAndSaveResources() {
        List<String> savedResourseIds = newsResourcesService.findAll()
                .stream()
                .map(NewsResource::getApiId)
                .collect(Collectors.toList());
        List<NewsResource> resources = dataExtractionService.extractResources()
                .stream()
                .filter(it -> !savedResourseIds.contains(it.getApiId()))
                .collect(Collectors.toList());
        newsResourcesService.saveAll(resources);
    }

    @Override
    @Scheduled(cron = "0 15 0 * * *")
    public void extractAndSaveNews() {
        List<NewsContent> newsContent = dataExtractionService.extractNews();
        newsContentService.saveAll(newsContent);
    }

    @Override
    @Scheduled(cron = "0 45 0 * * *")
    public void deleteOldNews() {
        LocalDateTime boundaryDate = LocalDateTime.now().minus(DEFAULT_PERIOD, ChronoUnit.DAYS);
        newsContentService.deleteContentBefore(boundaryDate);
    }

}
