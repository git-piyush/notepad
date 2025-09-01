package com.notepad.serviceImpl;

import com.notepad.dao.ArticleRepository;
import com.notepad.dao.CategotyRepository;
import com.notepad.entity.Article;
import com.notepad.entity.Category;
import com.notepad.restImpl.ArticleRestImpl;
import com.notepad.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategotyRepository categotyRepository;

    @Override
    public ResponseEntity<?> addNewArticle(Article article) {
        try {
                if(!Objects.isNull(article) && validateArticle(article)){
                    article.setPublication_date(new Date());
                    article.setCategory(categotyRepository.findById(article.getCategoryId()).get());
                    articleRepository.save(article);
                    return new ResponseEntity<>("{\"message\":\"Article added successfully\"}", HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("{\"message\":\"Invalid value for ("+article.getId()+")\"}", HttpStatus.BAD_REQUEST);
                }
        } catch (Exception e) {
            log.error("Exception in addNewCategory: {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllArticle() {
        try{
            List<Article> articleList = articleRepository.findAll();
            if(!Objects.isNull(articleList) && articleList.size()>0){
                for(int i=0;i<articleList.size();i++){
                    articleList.get(i).setCategoryId(articleList.get(i).getCategory().getId());
                    articleList.get(i).setCategoryName(articleList.get(i).getCategory().getName());
                }
                return new ResponseEntity<>(articleList, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("{\"message\":\"No Data.\"}", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.error("Exception in addNewCategory: {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllPublishedArticle() {
        try{
            List<Article> publishedArticleList = articleRepository.getAllPublishedArticle("Published");
            if(!Objects.isNull(publishedArticleList) && publishedArticleList.size()>0){
                for(int i=0;i<publishedArticleList.size();i++){
                    publishedArticleList.get(i).setCategoryId(publishedArticleList.get(i).getCategory().getId());
                    publishedArticleList.get(i).setCategoryName(publishedArticleList.get(i).getCategory().getName());
                }
                return new ResponseEntity<>(publishedArticleList, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("{\"message\":\"No Data.\"}", HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.error("Exception in addNewCategory: {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> updateArticle(Article article) {
        //To Do
        try {
            if(!Objects.isNull(article) && article.getId() !=null){

                Optional<Article> opDbArticle = articleRepository.findById(article.getId());
                if(!opDbArticle.isPresent()){
                    return new ResponseEntity<>("{\"message\":\"Article not present with article id:  ("+article.getId()+")\"}", HttpStatus.BAD_REQUEST);
                }
                Article dbArticle = opDbArticle.get();
                if(article.getTitle()!=null && article.getTitle().trim().length()>0){
                    dbArticle.setTitle(article.getTitle());
                }
                if(article.getContent()!=null && article.getContent().trim().length()>0){
                    dbArticle.setContent(article.getContent());
                }
                if(article.getStatus()!=null && article.getStatus().trim().length()>0){
                    dbArticle.setStatus(article.getStatus());
                }
                dbArticle.setPublication_date(new Date());
                articleRepository.save(dbArticle);
                return new ResponseEntity<>("{\"message\":\"Article updated successfully\"}", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("{\"message\":\"Required Article Id to update article.\"}", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Exception in addNewCategory: {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateArticle(Article article) {
        return !Objects.isNull(article) && StringUtils.hasText(article.getTitle())
                && StringUtils.hasText(article.getContent()) && StringUtils.hasText(String.valueOf(article.getCategoryId()));
    }
}
