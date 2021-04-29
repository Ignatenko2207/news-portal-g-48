package com.mainacademy.newsportal.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum NewsCategory {

    TOP("top", LocalizedProperty.builder()
            .en("TOP-NEWS")
            .ru("ГЛАВНЫЕ НОВОСТИ")
            .build()),
    GENERAL("general", LocalizedProperty.builder()
            .en("GENERAL")
            .ru("ВСЕ НОВОСТИ")
            .build()),
    BUSINESS("business", LocalizedProperty.builder()
            .en("BUSINESS")
            .ru("БИЗНЕС")
            .build()),
    SPORTS("sports", LocalizedProperty.builder()
            .en("SPORT")
            .ru("СПОРТ")
            .build()),
    ENTERTAINMENT("entertainment", LocalizedProperty.builder()
            .en("ENTERTAINMENT")
            .ru("РАЗВЛЕЧЕНИЯ")
            .build()),
    SCIENCE("science", LocalizedProperty.builder()
            .en("SCIENCE")
            .ru("НАУКА")
            .build()),
    HEALTH("health", LocalizedProperty.builder()
            .en("HEALTH")
            .ru("ЗДОРОВЬЕ")
            .build()),
    TECHNOLOGY("technology", LocalizedProperty.builder()
            .en("TECHNOLOGY")
            .ru("ТЕХНОЛОГИИ")
            .build());

    private final String id;
    private final LocalizedProperty localizedProperty;

    public static NewsCategory ofName(String name) {
        return Arrays.stream(values())
                .filter(it -> it.getId().equals(name.toLowerCase()))
                .findFirst()
                .orElse(NewsCategory.GENERAL);
    }

}
