package com.example.gotogether.cart.dto;

import com.example.gotogether.cart.entity.Cart;
import com.example.gotogether.product.dto.ProductOptionDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

public class CartDTO {


    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "장바구니 추가")
    @ToString
    public static class AddCartReqDTO {
        private Long productId;
        private Long productOptionId;
        private int numberOfPeople;
        private int singleRoomNumber;
    }


    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "장바구니 삭제")
    @ToString
    public static class CartDeleteReqDTO {
        private Long cartId;
    }


    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "장바구니 수정")
    @ToString
    public static class UpdateCartReqDTO {

        @ApiModelProperty(value = "수정할 ProductOption의 id")
        private Long productOptionId;
        @ApiModelProperty(value = "변경될 인원수")
        private int numberOfPeople;
        @ApiModelProperty(value = "변경될 싱글룸 개수")
        private int singleRoomNumber;

        @Builder
        public UpdateCartReqDTO(Cart cart) {
            this.productOptionId = cart.getProductOption().getProductOptionId();
            this.numberOfPeople = cart.getNumberOfPeople();
            this.singleRoomNumber = cart.getSingleRoomNumber();
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "장바구니 조회")
    @ToString
    public static class CartListResDTO {
        private Long cartId;
        private Long productId;
        private String productName;
        private String productThumbnail;
        private int productPrice;
        private int numberOfPeople;
        private int singleRoomNumber;
        private ProductOptionDTO.ProductOptionResForCart option;

        public CartListResDTO(Cart cart){
            this.cartId = cart.getCartId();
            this.productId = cart.getProduct().getProductId();
            this.productName = cart.getProduct().getName();
            this.productThumbnail = cart.getProduct().getThumbnail();
            this.productPrice = cart.getProduct().getPrice();
            this.numberOfPeople = cart.getNumberOfPeople();
            this.singleRoomNumber = cart.getSingleRoomNumber();
            this.option = new ProductOptionDTO.ProductOptionResForCart(cart.getProductOption());
        }

    }

}
