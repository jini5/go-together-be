package com.example.gotogether.product.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"상품 옵션 서비스"}, description = "상품 옵션 추가, 상품 옵션 수정, 상품 옵션 삭제, 상품 옵션 조회")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminProductOptionController {


}
