package com.notepad.restImpl;

import com.notepad.entity.Article;
import com.notepad.rest.ArticleRest;
import com.notepad.service.ArticleService;
import com.notepad.serviceImpl.CategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleRestImpl implements ArticleRest {

    Logger log = LoggerFactory.getLogger(ArticleRestImpl.class);

    @Autowired
    ArticleService articleService;

    @Override
    public ResponseEntity<?> addNewArticle(Article article) {
        try {
            return articleService.addNewArticle(article);
        } catch (Exception e) {
            log.error("Exception in addNewArticle: {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllArticle() {
        try {
            return articleService.getAllArticle();
        } catch (Exception e) {
            log.error("Exception in getAllArticle: {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllPublishedArticle() {
        try {
            return articleService.getAllPublishedArticle();
        } catch (Exception e) {
            log.error("Exception in getAllPublishedArticle: {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateArticle(Article article) {
        try {
            return articleService.updateArticle(article);
        } catch (Exception e) {
            log.error("Exception in updateArticle: {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
