package com.example.gotogether.category.service;

import com.example.gotogether.category.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<?> makeCate(CategoryDTO.makeCate dto);
    ResponseEntity<?> selectCate();
}
