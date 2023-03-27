package com.example.gotogether.product.service.Impl;

import com.example.gotogether.product.dto.ProductOptionDTO;
import com.example.gotogether.product.entity.ProductOption;
import com.example.gotogether.product.repository.ProductOptionRepository;
import com.example.gotogether.product.repository.ProductRepository;
import com.example.gotogether.product.service.ProductOptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductOptionServiceImpl implements ProductOptionService {

    private ProductOptionRepository productOptionRepository;

    @Override
    public ResponseEntity<?> createProductOptions(Long productId, ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO) {

        try {

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity<?> updateProductOptions(Long productId, ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO) {

        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> deleteProductOption(Long productOptionId) {
        try {
            productOptionRepository.deleteAllBy(productOptionId).orElseThrow(IllegalArgumentException::new);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getAllProductOptions(Long productId, ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO) {
        try {

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
