package com.notepad.rest;

import com.notepad.entity.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path="/article")
public interface ArticleRest {

    @PostMapping(path = "/addNewArticle")
    ResponseEntity<?> addNewArticle(@RequestBody(required = true)Article article);

    @GetMapping(path = "/getAllArticle")
    ResponseEntity<?> getAllArticle();

    @GetMapping(path = "/getAllPublishedArticle")
    ResponseEntity<?> getAllPublishedArticle();

    @PostMapping(path="/updateArticle")
    ResponseEntity<?> updateArticle(@RequestBody(required = true) Article article);

    @GetMapping(path="/deleteArticle/{id}")
    ResponseEntity<?> deleteArticle(@PathVariable(required = true) Long id);

}
