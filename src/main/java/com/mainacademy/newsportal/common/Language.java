package com.mainacademy.newsportal.common;

public enum Language {
    UA,
    RU,
    EN;

    public static Language of(String language) {
        switch (language.toUpperCase()) {
            case "EN": return Language.EN;
            case "RU": return Language.RU;
            case "UA":
            default: return Language.UA;
        }
    }
}
