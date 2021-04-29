package com.mainacademy.newsportal.common;

public enum Language {
    RU,
    EN;

    public static Language of(String language) {
        switch (language.toUpperCase()) {
            case "EN": return Language.EN;
            case "RU":
            default: return Language.RU;
        }
    }
}
