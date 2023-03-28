package com.example.gotogether.product.service;

import com.example.gotogether.product.dto.ProductDTO;
import com.example.gotogether.product.entity.Product;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface ProductService {

    ResponseEntity<?> createProduct(ProductDTO.ProductCreateReqDTO productReqDTO);

    ResponseEntity<?> deleteProduct(Long productId);

    ResponseEntity<?> getAllProducts(int page);

    ResponseEntity<?> updateProduct(Long productId, ProductDTO.ProductUpdateReqDTO productReqDTO);

    ResponseEntity<?> findDetailProduct(Long productId);

    ResponseEntity<?> findProductByCategory(Long categoryId, int page);

    ResponseEntity<?> findProductByKeyword(String keyword, int page, String sort, LocalDate dateOption, int people);

    ResponseEntity<?> findPopularProducts(Long categoryId);
}
