package com.example.gotogether.category.controller;

import com.example.gotogether.category.dto.CategoryDTO;
import com.example.gotogether.category.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "카테고리 목록 조회", notes = "카테고리 목록을 JSON 형태로 조회.\n" +
            "code: 200 조회 성공, 204 표시할 내용 없음")
    public ResponseEntity<?> getCategories(){
        return categoryService.selectCate();
    }
}
