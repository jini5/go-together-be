package com.example.gotogether.category.controller;

import com.example.gotogether.category.dto.CategoryDTO;
import com.example.gotogether.category.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"카테고리 서비스"}, description = "카테고리 조회")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories(){
        return categoryService.selectCate();
    }
    @PatchMapping("/admin/categories/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO.UpdateCategory dto){
        return categoryService.updateCate(categoryId,dto);
    }
}
