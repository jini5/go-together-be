package com.example.gotogether.product.service;

import com.example.gotogether.product.dto.ProductDTO;
import com.example.gotogether.product.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ResponseEntity<?> createProduct(ProductDTO.ProductReqDTO productReqDTO);

    ResponseEntity<?> deleteProduct(Long productId);

    ResponseEntity<List<Product>> getAllProducts();

    ResponseEntity<?> patchProduct(Long productId, ProductDTO.ProductReqDTO productReqDTO);

    ResponseEntity<?> findProductByCategory(Long categoryId, int page);

}
