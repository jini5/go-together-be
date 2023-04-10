package com.example.gotogether.wishlist.dto;

import com.example.gotogether.wishlist.entity.Wishlist;
import io.swagger.annotations.ApiModel;
import lombok.*;

public class WishlistDTO {

    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "위시 추가")
    @ToString
    public static class WishReqDTO {
        private Long productId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ApiModel(value = "위시리스트 목록 조회")
    @ToString
    public static class WishlistResDTO {

        private Long wishlistId;
        private Long productId;
        private String productName;
        private String productThumbnail ;
        private String productFeature;

        @Override
        public String toString() {
            return "WishlistResDTO{" +
                    "wishlistId=" + wishlistId +
                    ", productId=" + productId +
                    ", productName='" + productName + '\'' +
                    ", productThumbnail='" + productThumbnail  + '\'' +
                    ", productFeature='" + productFeature + '\'' +
                    '}';
        }

        public WishlistResDTO(Wishlist wishlist) {
            wishlistId = wishlist.getWishlistId();
            productId = wishlist.getProduct().getProductId();
            productName = wishlist.getProduct().getName();
            productThumbnail  = wishlist.getProduct().getThumbnail();
            productFeature = wishlist.getProduct().getFeature();

        }

    }

}
