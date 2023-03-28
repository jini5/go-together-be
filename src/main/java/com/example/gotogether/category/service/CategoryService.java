package com.example.gotogether.category.service;

import com.example.gotogether.category.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<?> makeCate(CategoryDTO.MakeCategory dto);

    ResponseEntity<?> selectCate();

    ResponseEntity<?> updateCate(Long categoryId, CategoryDTO.UpdateCategory dto);

    ResponseEntity<?> deleteCate(Long categoryId);

    ResponseEntity<?> viewCategoryDetail(Long categoryId);
}
