package com.example.gotogether.page.controller;

import com.example.gotogether.page.service.PageContentsService;
import com.example.gotogether.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PageContentsController {

    private final PageContentsService pageContentsService;
    private final ProductService productService;

    @GetMapping("/page/region")
    public ResponseEntity<?> getRegionList(){
        return pageContentsService.getRegionList();
    }

    @GetMapping("/page/popular/products")
    @ApiOperation(value = "인기 상품 검색(전체 or 특정 카테고리 가능)", notes = "categoryID 를 받을경우 그 카테고리의 인기순 상품 10개 제공.없을시 전체 상품 중 인기순 10개 제공. \n" +
            "code: 200 상품 목록 조회 성공, 204 표시할 상품 없음, 400 잘못된 카테고리 요청, 500 서버에러 ")
    public ResponseEntity<?> findPopularProducts(@RequestParam(required = false) Long categoryId){
        return productService.findPopularProducts(categoryId);
    }
}
