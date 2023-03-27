package com.example.gotogether.product.service.Impl;

import com.example.gotogether.category.entity.Category;
import com.example.gotogether.category.repository.CategoryRepository;
import com.example.gotogether.global.response.PageResponseDTO;
import com.example.gotogether.product.dto.ProductDTO;
import com.example.gotogether.product.dto.ProductOptionDTO;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductCategory;
import com.example.gotogether.product.entity.ProductOption;
import com.example.gotogether.product.entity.ProductStatus;
import com.example.gotogether.product.repository.ProductCategoryRepository;
import com.example.gotogether.product.repository.ProductRepository;
import com.example.gotogether.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static com.example.gotogether.global.config.PageSizeConfig.Product_List_By_Category;
import static com.example.gotogether.global.config.PageSizeConfig.Product_List_By_Admin;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public ResponseEntity<?> createProduct(ProductDTO.ProductReqDTO productReqDTO) {
        try {
            if (productRepository.existsByName(productReqDTO.getName()))return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //상품 엔티티 생성
            Product product = productReqDTO.toEntity();
            //상품 카테고리 엔티티 생성 후 상품엔티티의 카테고리 리스트에 넣기
            List<Category> categoryList = categoryRepository.findAllByCategoryIdIn(productReqDTO.getCategoryIdList());
            if (categoryList.size() != productReqDTO.getCategoryIdList().size())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            for (Category category : categoryList) {
                product.getCategories().add(ProductCategory.builder()
                        .category(category)
                        .product(product)
                        .build());
            }
            //상품 옵션 생성 후, 상품 엔티티의 옵션 리스트에 넣기
            for (ProductOptionDTO.ProductOptionCreateReqDTO createDto : productReqDTO.getOptions()) {
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
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteProduct(Long productId) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
            product.changeStatusHiding(product);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateProduct(Long productId, ProductDTO.ProductReqDTO productReqDTO) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
            if (product == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            productCategoryRepository.deleteAllByProduct(product);

            List<Category> categoryList = categoryRepository.findAllByCategoryIdIn(productReqDTO.getCategoryIdList());
            if (categoryList.size() != productReqDTO.getCategoryIdList().size())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            List<ProductCategory> productCategories = new ArrayList<>();
            for (Category category : categoryList) {
                productCategories.add(ProductCategory.builder()
                        .category(category)
                        .product(product)
                        .build());
            }
            product.update(productReqDTO,productCategories);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getAllProducts(int page) {
        try {
            if (page < 1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            PageRequest pageable = PageRequest.of(page - 1, Product_List_By_Admin);
            Page<Product> productList = productRepository.findAll(pageable);
            PageResponseDTO pageResponseDTO = new PageResponseDTO(productList);
            pageResponseDTO.setContent(
                    pageResponseDTO
                            .getContent()
                            .stream()
                            .map(e -> new ProductDTO.ProductListResDTO((Product)e))
                            .collect(Collectors.toList())
            );
            return new ResponseEntity<>(pageResponseDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> findDetailProduct(Long productId) {
        try {
            Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
            return new ResponseEntity<>(new ProductDTO.ProductDetailResDTO(product),HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ResponseEntity<?> findProductByCategory(Long categoryId, int page) {
        try {
            if (page < 1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            PageRequest pageable = PageRequest.of(page - 1, Product_List_By_Category);
            //카테고리 검색
            Category category = categoryRepository.findById(categoryId).orElseThrow(IllegalArgumentException::new);
            //해당 카테고리 및 관련 하위 카테고리 전부 중 하나라도 포함 한 상품-카테고리 검색
            Page<ProductCategory> productCategories = productCategoryRepository.findAllByCategoryIn(pageable, listOfCategory(category));
            //페이지 처리 한 상태로 변환
            if (productCategories.getTotalElements() == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            PageResponseDTO pageResponseDTO = new PageResponseDTO(productCategories);
            //상품-카테고리 상태이므로, 상품 정보만 꺼내기 위해 일단 리스트 꺼내기
            List<ProductCategory> productCategoryList = (List<ProductCategory>) pageResponseDTO.getContent();
            // 상품 정보만 반환 하기 위해 stream 을 이용해 dto 생성자 호출.
            List<ProductDTO.ProductListResDTO> productListResDTOS = productCategoryList
                    .stream()
                    .map(e -> new ProductDTO.ProductListResDTO(e.getProduct()))
                    .collect(Collectors.toList());
            // 페이징 처리된 리스트노출용 상품 정보로 전환
            pageResponseDTO.setContent(productListResDTOS);
            return new ResponseEntity<>(pageResponseDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findProductByKeyword(String keyword, int page,String sort) {
        try {
            if (page < 1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            PageRequest pageable = PageRequest.of(page - 1, Product_List_By_Category);
            Page<Product> productPage = productRepository
                    .searchByKeywordAndSorting(pageable,keyword,sort);
            if (productPage.getTotalElements()<1){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            PageResponseDTO pageResponseDTO = new PageResponseDTO(productPage);
            pageResponseDTO
                    .setContent(pageResponseDTO.getContent()
                            .stream()
                            .map(e -> new ProductDTO.ProductListResDTO((Product) e))
                            .collect(Collectors.toList()));
            return new ResponseEntity<>(pageResponseDTO,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findPopularProducts() {
        List<Product> productList = productRepository.findPopular();
        System.out.println(productList.size());
        if (productList.size()<1){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        for (Product product : productList){
            System.out.println(product.getProductId());
        }
        return new ResponseEntity<>(productList.stream().map(e -> new ProductDTO.ProductListResDTO(e)).collect(Collectors.toList()), HttpStatus.OK);
    }

    public List<Category> listOfCategory(Category category) {
        List<Category> categoryList = new ArrayList<>(category.getChildren());
        categoryList.add(category);
        for (int i = 0; i < category.getChildren().size(); i++) {
            Category insideCategory = category.getChildren().get(i);
            if (insideCategory.getChildren().size() > 0)
                categoryList.addAll(listOfCategory(insideCategory));
        }
        return categoryList;
    }


}
