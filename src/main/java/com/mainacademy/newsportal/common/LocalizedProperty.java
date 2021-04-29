package com.mainacademy.newsportal.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Locale;

@Setter
@Getter
@Builder
public class LocalizedProperty {

    private String ru;
    private String en;

    public String getByLocale(Locale locale) {
        switch (locale.getLanguage()) {
            case "en": return en;
            case "ru":
            default: return ru;
        }
    }

}
