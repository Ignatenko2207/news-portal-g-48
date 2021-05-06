package com.mainacademy.newsportal.processor;

public interface SchedulingProcessor {

    void extractAndSaveResources();
    void extractAndSaveNews();
    void deleteOldNews();
}
