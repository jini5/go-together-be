package com.example.gotogether.product.service.Impl;

import com.example.gotogether.category.entity.Category;
import com.example.gotogether.category.repository.CategoryRepository;
import com.example.gotogether.product.dto.ProductDTO;
import com.example.gotogether.product.dto.ProductOptionDTO;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductCategory;
import com.example.gotogether.product.entity.ProductOption;
import com.example.gotogether.product.repository.ProductRepository;
import com.example.gotogether.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> createProduct(ProductDTO.ProductReqDTO productReqDTO){
        try{
            //상품 엔티티 생성
            Product product = productReqDTO.toEntity();
            //상품 카테고리 엔티티 생성 후 상품엔티티의 카테고리 리스트에 넣기
            List<Category> categoryList =categoryRepository.findAllByCategoryId(productReqDTO.getCategoryIdList());
            if (categoryList.size()!=productReqDTO.getCategoryIdList().size())return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            for (Category category : categoryList){
                product.getCategories().add(ProductCategory.builder()
                        .category(category)
                        .product(product)
                        .build());
            }
            //상품 옵션 생성 후, 상품 엔티티의 옵션 리스트에 넣기
            for (ProductOptionDTO.ProductOptionCreateReqDTO createDto : productReqDTO.getOptions()){
                product.getProductOptions().add(ProductOption.builder()
                        .product(product)
                        .startDate(createDto.getStartDate())
                        .endDate(createDto.getEndDate())
                        .maxPeople(createDto.getMaxPeople())
                        .maxSingleRoom(createDto.getMaxSingleRoom())
                        .presentPeopleNumber(0)
                        .presentSingleRoomNumber(0)
                        .build());
            }

            productRepository.save(product);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> deleteProduct(Long productId){
        try{
            Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
            product.changeStatusHiding(product);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }



    @Override
    public ResponseEntity<List<Product>> getAllProducts(){
        try{
            return new ResponseEntity<>(productRepository.findAll(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> patchProduct(Long productId, ProductDTO.ProductReqDTO productReqDTO) {
        try{

            Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
            if(product ==null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            product.update(productReqDTO.toEntity());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




}
