package com.example.gotogether.product.controller;

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


    @PostMapping("/productOptions/{productId}")
    @ApiOperation(value = "상품 옵션 추가", notes = "상품의 옵션을 추가\n\n" + "code: 200 상품 옵션 추가 성공, code: 400 상품을 찾을 수 없음, code:500: 서버 에러")
    public ResponseEntity<?> createProduct(@PathVariable Long productId, @RequestBody ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO) {
        return productOptionService.createProductOptions(productId, productOptionReqDTO);
    }


    //상품 삭제
    @DeleteMapping("/productOptions/{productOptionId}")
    @ApiOperation(value = "상품 옵션 삭제", notes = "상품의 옵션을 삭제합니다.\n\n" + "code: 200 상품 옵션 삭제 성공, code: 403 권한 없음, code: 404 표시할 상품이 없음, code: 500 서버에러\"")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productOptionId) {
        return productOptionService.deleteProductOption(productOptionId);
    }

    @PatchMapping("/productOptions/{productOptionId}")
    @ApiOperation(value = "상품 옵션 수정", notes = "상품의 옵션을 수정합니다.\n\n" + "code: 200 상품 옵션 수정 성공, code: 403 권한 없음, code: 404 표시할 상품 옵션이 없음, code: 500 서버에러\"")
    public ResponseEntity<?> updateProducts(@PathVariable Long productOptionId, @RequestBody ProductOptionDTO.OptionUpdateReqDTO optionUpdateReqDTO) {
        return productOptionService.updateProductOptions(productOptionId, optionUpdateReqDTO);
    }

    @GetMapping("/productOptions")
    @ApiOperation(value = "상품 옵션 전체 목록 조회", notes = "상품의 전체 옵션 목록들을 조회합니다.\n\n" + "code: 200 상품 옵션 전체 조회 성공, code: 204 표시할 상품 옵션이 없음, code: 403 권한 없음, code: 500 서버에러\"")
    public ResponseEntity<?> allProductOptions(Long productId) {
        return productOptionService.getAllProductOptions(productId);
    }

}
