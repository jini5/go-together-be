package com.example.gotogether.product.controller;

import com.example.gotogether.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"상품 서비스"}, description = "상품 추가, 상품수정, 상품삭제(숨김)")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/categories/{categoryId}")
    @ApiOperation(value = "카테고리로 상품 검색", notes = "해당 카테고리와 무한 하위 카테고리까지 관련된 상품 반환.\n" +
            "code: 200 상품 목록 조회 성공, 204 표시할 상품 없음, 400 잘못된 페이지 사이즈 요청, 404 해당 카테고리가 존재하지 않음. 500 서버에러 ")
    public ResponseEntity<?> findProductByCategory(@PathVariable Long categoryId,@RequestParam(required = false, defaultValue = "1") int page){
        return productService.findProductByCategory(categoryId,page);
    }
}
