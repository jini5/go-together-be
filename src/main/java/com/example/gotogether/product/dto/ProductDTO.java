package com.example.gotogether.product.dto;

import com.example.gotogether.category.dto.CategoryDTO;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductCategory;
import com.example.gotogether.product.entity.ProductStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {


    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "상품 추가")
    @ToString
    public static class ProductReqDTO {

        @ApiModelProperty(value = "해당 상품 카테고리", required = true)
        private List<Long> categoryIdList = new ArrayList<>();
        @ApiModelProperty(value = "상품 명", required = true)
        private String name;
        @ApiModelProperty(value = "상품 요약", required = true)
        private String summary;
        @ApiModelProperty(value = "여행 지역", required = true)
        private String area;
        @ApiModelProperty(value = "여행 특징", required = true)
        private String feature;
        @ApiModelProperty(value = "항공편", required = true)
        private String airplane;
        @ApiModelProperty(value = "싱글룸 가격", required = true)
        private int singleRoomPrice;
        @ApiModelProperty(value = "상품 가격", required = true)
        private int price;
        @ApiModelProperty(value = "상품 여행 유형", required = true)
        private String type;
        @ApiModelProperty(value = "상품 썸네일", required = true)
        private String thumbnail;
        @ApiModelProperty(value = "상품 상세 정보", required = true)
        private String detail;
        @ApiModelProperty(value = "상품 상태", required = true)
        @Enumerated(value = EnumType.STRING)
        private ProductStatus productStatus;
        @ApiModelProperty(value = "상품 옵션 리스트", required = false)
        private List<ProductOptionDTO.ProductOptionReqDTO> options;

//        public ProductStatus setEnumProductStatus(String status) {
//            if (status.equals(ProductStatus.FOR_SALE)) {
//                return ProductStatus.FOR_SALE;
//            } else if (productStatus.equals(ProductStatus.STOP_SELLING)) {
//                return ProductStatus.STOP_SELLING;
//            }
//            return ProductStatus.HIDING;
//        }

        public Product toEntity() {
            return Product.builder()
                    .name(name)
                    .summary(summary)
                    .area(area)
                    .feature(feature)
                    .airplane(airplane)
                    .singleRoomPrice(singleRoomPrice)
                    .price(price)
                    .type(type)
                    .thumbnail(thumbnail)
                    .detail(detail)
                    .productStatus(productStatus)
                    .build();
        }


    }


    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "상품 목록")
    public static class ProductListResDTO {
        @ApiModelProperty(value = "해당 상품 카테고리", required = true)
        private List<CategoryDTO.viewCategoryForProduct> categories = new ArrayList<>();

        @ApiModelProperty(value = "상품 ID", required = true)
        private Long productId;
        @ApiModelProperty(value = "상품 명", required = true)
        private String productName;
        @ApiModelProperty(value = "상품 요약", required = true)
        private String productSummary;
        @ApiModelProperty(value = "여행 지역", required = true)
        private String productArea;
        @ApiModelProperty(value = "상품 썸네일", required = true)
        private String productThumbnail;


        private ProductStatus productStatus;

        public ProductListResDTO(Product product) {
            this.categories = product.getCategories().stream()
                    .map(e -> new CategoryDTO.viewCategoryForProduct(e.getCategory()))
                    .collect(Collectors.toList());
            this.productId = product.getProductId();
            this.productName = product.getName();
            this.productSummary = product.getSummary();
            this.productArea = product.getArea();
            this.productThumbnail = product.getThumbnail();
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "상품 상세 정보 조회")
    public static class ProductDetailResDTO {
        @ApiModelProperty(value = "해당 상품 카테고리", required = true)
        private List<CategoryDTO.viewCategoryForProduct> categories = new ArrayList<>();
        @ApiModelProperty(value = "해당 상품 옵션", required = true)
        private List<ProductOptionDTO.ProductOptionResDTO> productOptions = new ArrayList<>();
        @ApiModelProperty(value = "상품 ID", required = true)
        private Long productId;
        @ApiModelProperty(value = "상품 명", required = true)
        private String name;
        @ApiModelProperty(value = "상품 요약", required = true)
        private String summary;
        @ApiModelProperty(value = "여행 지역", required = true)
        private String area;
        @ApiModelProperty(value = "여행 특징", required = true)
        private String feature;
        @ApiModelProperty(value = "항공편", required = true)
        private String airplane;
        @ApiModelProperty(value = "싱글룸 가격", required = true)
        private int singleRoomPrice;
        @ApiModelProperty(value = "상품 가격", required = true)
        private int price;
        @ApiModelProperty(value = "상품 여행 유형", required = true)
        private String type;
        @ApiModelProperty(value = "상품 썸네일", required = true)
        private String thumbnail;
        @ApiModelProperty(value = "상품 상세 정보", required = true)
        private String detail;

        private ProductStatus productStatus;

        public ProductDetailResDTO(Product product) {
            this.categories = product.getCategories().stream()
                    .map(e -> new CategoryDTO.viewCategoryForProduct(e.getCategory()))
                    .collect(Collectors.toList());
            this.productOptions = product.getProductOptions().stream()
                    .map(e -> new ProductOptionDTO.ProductOptionResDTO(e))
                    .collect(Collectors.toList());
            this.productId = product.getProductId();
            this.name = product.getName();
            this.summary = product.getSummary();
            this.area = product.getArea();
            this.feature = product.getFeature();
            this.airplane = product.getAirplane();
            this.singleRoomPrice = product.getSingleRoomPrice();
            this.price = product.getPrice();
            this.type = product.getType();
            this.thumbnail = product.getThumbnail();
            this.detail = product.getDetail();
            this.productStatus = product.getProductStatus();
        }
    }

}

