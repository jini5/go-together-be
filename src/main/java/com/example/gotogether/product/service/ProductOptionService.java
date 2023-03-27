package com.example.gotogether.product.service;

import com.example.gotogether.product.dto.ProductOptionDTO;
import org.springframework.http.ResponseEntity;

public interface ProductOptionService {
    ResponseEntity<?> createProductOptions(Long productId, ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO);

    ResponseEntity<?> updateProductOptions(Long productId, ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO);

    ResponseEntity<?> deleteProductOption(Long productOptionId);

    ResponseEntity<?> getAllProductOptions(Long productId, ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO);

}
