package com.notepad.serviceImpl;

import com.notepad.dao.CategotyRepository;
import com.notepad.entity.Category;
import com.notepad.entity.UserInfo;
import com.notepad.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategotyRepository categotyRepository;

    @Override
    public ResponseEntity<?> addNewCategory(Category category) {
        try {
            if(!validateCategory(category)){
                return new ResponseEntity<>("{\"message\":\"Missing Required Data.\"}", HttpStatus.BAD_REQUEST);
            }
            Boolean dbCategory = categotyRepository.existsByNameIgnoreCase(category.getName());
            if(dbCategory){
                return new ResponseEntity<>("{\"message\":\"Category Already Exist.\"}", HttpStatus.FOUND);
            }
            categotyRepository.save(category);
            return new ResponseEntity<>("{\"message\":\"Category Added.\"}", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception in addNewCategory: {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> getAllCategory() {
        try {
            List<Category> categoryList = categotyRepository.findAll();
            if(categoryList!=null && categoryList.size()>0){
                return new ResponseEntity<>(categoryList, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("{\"message\":\"No Data.\"}", HttpStatus.OK);
            }
        }catch (Exception e){
            log.error("Exception in getAllCategory : {}", e.getMessage());
        }
        return new ResponseEntity<>("{\"message\":\"Something went wrong.\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategory(Category category) {
        return !Objects.isNull(category) && StringUtils.hasText(category.getName());
    }

}
