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
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ResponseEntity<?> createProductOptions(Long productId, ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
            ProductOption productOption = productOptionReqDTO.toEntity(product);
            product.getProductOptions().add(productOption);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    @Transactional
    public ResponseEntity<?> updateProductOptions(Long productOptionId, ProductOptionDTO.OptionUpdateReqDTO optionUpdateReqDTO) {

        try {
            ProductOption productOption = productOptionRepository.findByProductOptionId(productOptionId).orElseThrow(NoSuchElementException::new);
            productOption.update(optionUpdateReqDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteProductOption(Long productOptionId) {
        try {
            productOptionRepository.deleteAllByProductOptionId(productOptionId).orElseThrow(NoSuchElementException::new);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getAllProductOptions(Long productId) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
            List<ProductOption> productOptionList= productOptionRepository.findAllByProduct(product);
            if (productOptionList.size()<1)return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(productOptionList.stream().map(ProductOptionDTO.ProductOptionResDTO::new).collect(Collectors.toList()), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
