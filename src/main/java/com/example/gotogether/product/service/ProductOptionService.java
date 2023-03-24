package com.example.gotogether.product.service;

import com.example.gotogether.product.dto.ProductOptionDTO;
import org.springframework.http.ResponseEntity;

public interface ProductOptionService {
    ResponseEntity<?> createProductOptions(ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO);

    ResponseEntity<?> patchProductOptions(ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO);

    ResponseEntity<?> deleteProductOptions(ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO);

    ResponseEntity<?> AllProductOptions(ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO);
}
