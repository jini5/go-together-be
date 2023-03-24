package com.example.gotogether.product.dto;

import com.example.gotogether.product.entity.ProductOption;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
public class ProductOptionDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class ProductOptionCreateReqDTO{
        @ApiModelProperty(value = "출발일", required = true)
        private LocalDate startDate;
        @ApiModelProperty(value = "도착일", required = true)
        private LocalDate endDate;
        @ApiModelProperty(value = "최대 인원", required = true)
        private int maxPeople;
        @ApiModelProperty(value = "최대 싱글룸 수", required = true)
        private int maxSingleRoom;
    }

    @Getter
    public static class ProductOptionReqDTO{
        @ApiModelProperty(value = "상품 기간 상세 ID", required = true)
        private Long ProductOptionId;

        @ApiModelProperty(value = "출발일", required = true)
        private LocalDate startDate;

        @ApiModelProperty(value = "도착일", required = true)
        private LocalDate endDate;

        @ApiModelProperty(value = "최대 인원 수", required = true)
        private int maxPeople;

        @ApiModelProperty(value = "최대 싱글룸 수", required = true)
        private int maxSingleRoom;

        @ApiModelProperty(value = "현재 예약 인원 수 ", required = true)
        private int presentPeopleNumber;

        @ApiModelProperty(value = "현재 예약 싱글룸 수", required = true)
        private int presentSingleRoomNumber;


    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductOptionResDTO{

        @ApiModelProperty(value = "상품 기간 상세 ID", required = true)
        private Long ProductOptionId;

        @ApiModelProperty(value = "출발일", required = true)
        private LocalDate startDate;

        @ApiModelProperty(value = "도착일", required = true)
        private LocalDate endDate;

        @ApiModelProperty(value = "최대 인원 수", required = true)
        private int maxPeople;

        @ApiModelProperty(value = "최대 싱글룸 수", required = true)
        private int maxSingleRoom;

        @ApiModelProperty(value = "현재 예약 인원 수 ", required = true)
        private int PresentPeopleNumber;

        @ApiModelProperty(value = "현재 예약 싱글룸 수", required = true)
        private int PresentSingleRoomNumber;

        public ProductOptionResDTO(ProductOption productOption) {
            this.ProductOptionId = productOption.getProductOptionId();
            this.startDate = productOption.getStartDate();
            this.endDate = productOption.getEndDate();
            this.maxPeople = productOption.getMaxPeople();
            this.maxSingleRoom = productOption.getMaxSingleRoom();
            this.PresentPeopleNumber = productOption.getPresentPeopleNumber();
            this.PresentSingleRoomNumber = productOption.getPresentSingleRoomNumber();
        }
    }
}
