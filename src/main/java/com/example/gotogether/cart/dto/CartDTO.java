package com.example.gotogether.cart.dto;

import com.example.gotogether.cart.entity.Cart;
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
    public static class AddCartReqDTO{
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
    public static class CartDeleteReqDTO{
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
    public static class CartListResDTO{
        private Long cartId;
        private Long productId;
        private Long productOptionId;
        private String productName;
        private String productThubnail;
        private int numberOfPeople;
        private int singleRoomNumber;

        public CartListResDTO(Long cartId, Long productId, Long productOprionId, String productName, String productThubnail, int numberOfPeople, int singleRoomNumber) {
            this.cartId = cartId;
            this.productId = productId;
            this.productOptionId = productOptionId;
            this.productName = productName;
            this.productThubnail = productThubnail;
            this.numberOfPeople = numberOfPeople;
            this.singleRoomNumber = singleRoomNumber;
        }
    }

}
