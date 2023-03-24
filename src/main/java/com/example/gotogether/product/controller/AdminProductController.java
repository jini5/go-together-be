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
        System.out.println(productReqDTO.toString());
        return productService.createProduct(productReqDTO);
    }

    //상품 삭제
    @DeleteMapping("/products/{productId}")
    @ApiOperation(value = "상품 숨김(삭제)", notes = "상품 숨김처리(삭제) ")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    @PatchMapping("/products/{productId}")
    public ResponseEntity<?> patchProducts(@PathVariable Long productId,@RequestBody ProductDTO.ProductReqDTO productReqDTO) {
        return productService.patchProduct(productId, productReqDTO);
    }



    // 제품 전체목록 보기
    @GetMapping("/products")
    public ResponseEntity<List<Product>> allProducts() {
        return productService.getAllProducts();
    }





//    // 제품 상세 보기
//    @GetMapping("/products/details/{productId}")
//    public ResponseEntity<?> oneProduct(@PathVariable Long productId) {
//        return new
//    }



}
