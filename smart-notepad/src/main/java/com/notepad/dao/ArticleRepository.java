package com.notepad.dao;

import com.notepad.entity.Article;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Modifying
    @Transactional
    @Query("SELECT a FROM Article a WHERE a.status IS NULL OR a.status =:status")
    List<Article> getAllArticle(@Param("status") String status);

    @Query("UPDATE Article a set a.title =:title, a.content =:content, a.category.id =:categoryId, a.publication_date =:publication_date, a.status =:status where a.id =:id" )
    Integer updateArticle(@Param("title") String title, @Param("content") String content, @Param("categoryId") Integer categoryId,
                          @Param("publication_date") Date publication_date, @Param("status") String status, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE from Article a WHERE a.id =:id" )
    Integer deleteArticle(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("SELECT a FROM Article a WHERE a.status =:published")
    List<Article> getAllPublishedArticle(String published);
}
