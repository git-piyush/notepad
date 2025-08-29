package com.notepad.restImpl;

import com.notepad.entity.Category;
import com.notepad.rest.CategoryRest;
import com.notepad.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestImpl implements CategoryRest {

    Logger log = LoggerFactory.getLogger(CategoryRestImpl.class);

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<?> addNewCategory(Category category) {
        try {
            return categoryService.addNewCategory(category);
        } catch (Exception e) {
            log.error("Exception in addNewCategory: {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
