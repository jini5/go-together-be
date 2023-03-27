package com.example.gotogether.product.controller;

import com.example.gotogether.product.dto.ProductDTO;
import com.example.gotogether.product.dto.ProductOptionDTO;
import com.example.gotogether.product.service.ProductOptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"관리자 상품 옵션 서비스"}, description = "상품 옵션 추가, 상품 옵션 수정, 상품 옵션 삭제, 상품 옵션 조회")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminProductOptionController {

    private final ProductOptionService productOptionService;


    @PostMapping("/productOptions")
    @ApiOperation(value = "상품 옵션 추가", notes = "상품 옵션 추가")
    public ResponseEntity<?> createProduct(@RequestBody Long productId, ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO) {
        return productOptionService.createProductOptions(productId, productOptionReqDTO);
    }

    //상품 삭제
    @DeleteMapping("/productOptions/{productOptionId}")
    @ApiOperation(value = "상품 옵션 삭제", notes = "상품 옵션 삭제")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productOptionId) {
        return productOptionService.deleteProductOption(productOptionId);
    }

    @PatchMapping("/productOptions/{productOptionId}")
    @ApiOperation(value = "상품 옵션 수정", notes = "상품 옵션 수정")
    public ResponseEntity<?> updateProducts(@PathVariable Long productOptionId, @RequestBody ProductOptionDTO.OptionUpdateReqDTO optionUpdateReqDTO) {
        return productOptionService.updateProductOptions(productOptionId,optionUpdateReqDTO);
    }

    @GetMapping("/productOptions")
    @ApiOperation(value = "상품 옵션 전체 목록 보기", notes = "상품 옵션 전체 목록 보기")
    public ResponseEntity<?> allProductOptions(Long productId) {
        return productOptionService.getAllProductOptions(productId);
    }

}
