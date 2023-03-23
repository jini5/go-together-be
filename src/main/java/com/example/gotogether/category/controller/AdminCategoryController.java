package com.example.gotogether.category.controller;

import com.example.gotogether.category.dto.CategoryDTO;
import com.example.gotogether.category.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"관리자 카테고리 서비스"}, description = "카테고리 생성, 수정, 삭제")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<?> makeCate(@RequestBody CategoryDTO.MakeCategory dto){
        return categoryService.makeCate(dto);
    }
    @PatchMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO.UpdateCategory dto){
        return categoryService.updateCate(categoryId,dto);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        return categoryService.deleteCate(categoryId);
    }


}
