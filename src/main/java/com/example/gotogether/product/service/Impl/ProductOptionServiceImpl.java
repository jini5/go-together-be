package com.example.gotogether.product.service.Impl;

import com.example.gotogether.product.dto.ProductOptionDTO;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductOption;
import com.example.gotogether.product.repository.ProductOptionRepository;
import com.example.gotogether.product.repository.ProductRepository;
import com.example.gotogether.product.service.ProductOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<?> createProductOptions(Long productId, ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO) {

        try {
            ProductOption productOption = productOptionReqDTO.toEntity();

            //productOptionRepository.save(productOptionReqDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    @Transactional
    public ResponseEntity<?> updateProductOptions(Long productOptionId, ProductOptionDTO.OptionUpdateReqDTO optionUpdateReqDTO) {

        try {
            ProductOption productOption = productOptionRepository.findByProductOptionId(productOptionId).orElseThrow(IllegalArgumentException::new);
            productOption.update(optionUpdateReqDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteProductOption(Long productOptionId) {
        try {
            productOptionRepository.deleteAllByProductOptionId(productOptionId).orElseThrow(IllegalArgumentException::new);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getAllProductOptions(Long productId) {
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
