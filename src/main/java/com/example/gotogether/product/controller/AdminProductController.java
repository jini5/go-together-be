package com.example.gotogether.product.controller;

import com.example.gotogether.product.dto.ProductDTO;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"관리자 상품 서비스"}, description = "상품 추가, 상품수정, 상품삭제(숨김)")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminProductController {

    private final ProductService productService;

    @PostMapping("/products")
    @ApiOperation(value = "상품 추가", notes = "상품 추가")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO.ProductReqDTO productReqDTO) {
        return productService.createProduct(productReqDTO);
    }

    //상품 삭제
    @DeleteMapping("/products/{productId}")
    @ApiOperation(value = "상품 숨김(삭제)", notes = "상품 숨김처리(삭제) ")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    @PatchMapping("/products/{productId}")
    @ApiOperation(value = "상품 수정", notes = "상품 수정(옵션 제외)")
    public ResponseEntity<?> updateProducts(@PathVariable Long productId, @RequestBody ProductDTO.ProductReqDTO productReqDTO) {
        return productService.updateProduct(productId, productReqDTO);
    }


    // 제품 전체목록 보기
    @GetMapping("/products")
    @ApiOperation(value = "상품 전체 목록 보기", notes = "상품 전체 목록 보기")
    public ResponseEntity<?> allProducts(@RequestParam(required = false, defaultValue = "1") int page) {
        return productService.getAllProducts(page);
    }


    // 제품 상세 보기
    @GetMapping("/products/details/{productId}")
    @ApiOperation(value = "상품 상세 정보", notes = "상품 상세 정보를 보여줍니다.")
    public ResponseEntity<?> oneProduct(@PathVariable Long productId) {
        return productService.findDetailProduct(productId);
    }


}
