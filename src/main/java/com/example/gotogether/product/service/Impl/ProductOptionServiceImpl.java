package com.example.gotogether.product.service.Impl;

import com.example.gotogether.product.dto.ProductOptionDTO;
import com.example.gotogether.product.repository.ProductRepository;
import com.example.gotogether.product.service.ProductOptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductOptionServiceImpl implements ProductOptionService {

    private ProductRepository productRepository;

    @Override
    public ResponseEntity<?> createProductOptions(ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO) {

        try {

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity<?> patchProductOptions(ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO) {

        try {

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> deleteProductOptions(ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO) {

        try {

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> AllProductOptions(ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO) {

        try {

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
