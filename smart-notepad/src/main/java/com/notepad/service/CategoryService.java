package com.notepad.service;

import com.notepad.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    ResponseEntity<?> addNewCategory(Category category);

    ResponseEntity<?> getAllCategory();

    ResponseEntity<?> updateCategory(Category category);
}
