package com.mainacademy.newsportal.api.rest;

import com.mainacademy.newsportal.api.dto.NewsContentResponseDTO;
import com.mainacademy.newsportal.handler.NewsContentHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("news")
@RequiredArgsConstructor
public class NewsContentController {

    private final NewsContentHandler newsContentHandler;

    @GetMapping("all")
    ResponseEntity<NewsContentResponseDTO> getNewsForAllCategories(Locale locale) {
        return new ResponseEntity<>(newsContentHandler.agregateNewsContent(locale), HttpStatus.OK);
    }

    @GetMapping("category/{category}")
    ResponseEntity<NewsContentResponseDTO> getNewsByCategory(
            @PathVariable String category,
            @RequestParam Integer page,
            @RequestParam Integer size,
            Locale locale) {
        return new ResponseEntity<>(newsContentHandler.getNewsByCategory(category, page, size, locale), HttpStatus.OK);
    }

    @GetMapping("one/{id}")
    ResponseEntity<NewsContentResponseDTO.NewsContentDTO> getNewsById(
            @PathVariable Integer id,
            Locale locale) {
        return new ResponseEntity<>(newsContentHandler.getNewsById(id, locale), HttpStatus.OK);
    }

    // TODO: implement search by text
}
