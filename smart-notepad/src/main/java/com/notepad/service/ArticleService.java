package com.notepad.service;

import com.notepad.entity.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ArticleService {
    ResponseEntity<?> addNewArticle(Article article);

    ResponseEntity<?> getAllArticle();

    ResponseEntity<?> getAllPublishedArticle();

    ResponseEntity<?> updateArticle(Article article);
}
