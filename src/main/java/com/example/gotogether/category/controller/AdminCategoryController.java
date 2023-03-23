package com.example.gotogether.category.controller;

import com.example.gotogether.category.dto.CategoryDTO;
import com.example.gotogether.category.service.CategoryService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"관리자 카테고리 서비스"}, description = "카테고리 생성, 수정, 삭제")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping("/makeCategory")
    public ResponseEntity<?> makeCate(@RequestBody CategoryDTO.makeCate dto){
        return categoryService.makeCate(dto);
    }

}
