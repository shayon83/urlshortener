package com.testkube.urlshortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class UrlShortenerController {
    private final Map<String, String> shortToOriginal = new HashMap<>();
    private final Map<String, String> originalToShort = new HashMap<>();
    private final String domain = "http://short.url/";

    @PostMapping("/shorten")
    public String shortenURL(@RequestBody String originalURL) {
        String shortURL = generateShortURL();
        shortToOriginal.put(shortURL, originalURL);
        originalToShort.put(originalURL, shortURL);
        return domain + shortURL;
    }

    @GetMapping("/{shortURL}")
    public ResponseEntity<Object> redirectToOriginalURL(@PathVariable String shortURL) {
        String originalURL = shortToOriginal.get(shortURL);
        if (originalURL != null) {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header("Location", originalURL).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private String generateShortURL() {
        // Generate a random short URL (for simplicity, you can use a more complex algorithm)
        return Integer.toString(new Random().nextInt(1000), 36);
    }
}