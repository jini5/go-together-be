package com.example.gotogether.reservation.dto;

import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductOption;
import com.example.gotogether.reservation.entity.Reservation;
import com.example.gotogether.reservation.entity.ReservationDetail;
import com.example.gotogether.reservation.entity.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

public class ReservationDetailDTO {

    @ApiModel(value = "사용자 페이지 예약 목록 조회 응답")
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UserListResDTO {

        @ApiModelProperty(value = "예약 상세 아이디")
        private Long reservationDetailId;
        @ApiModelProperty(value = "상품 아이디")
        private Long productId;
        @ApiModelProperty(value = "상품 썸네일")
        private String productThumbnail;
        @ApiModelProperty(value = "출발일")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;
        @ApiModelProperty(value = "도착일")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;
        @ApiModelProperty(value = "예약인원")
        private int reservationPeopleNumber;
        @ApiModelProperty(value = "예약 싱글룸 개수")
        private int reservationSingleRoomNumber;
        @ApiModelProperty(value = "싱글룸 가격")
        private int productSingleRoomPrice;
        @ApiModelProperty(value = "상품 가격")
        private int productPrice;
        @ApiModelProperty(value = "예약금액")
        private int detailTotalPrice;

        public UserListResDTO(ReservationDetail reservationDetail) {
            this.reservationDetailId = reservationDetail.getReservationDetailId();
            this.productId = reservationDetail.getProduct().getProductId();
            this.productThumbnail = reservationDetail.getProduct().getThumbnail();
            this.startDate = reservationDetail.getStartDate();
            this.endDate = reservationDetail.getEndDate();
            this.reservationPeopleNumber = reservationDetail.getNumberOfPeople();
            this.reservationSingleRoomNumber = reservationDetail.getSingleRoomNumber();
            this.productSingleRoomPrice = reservationDetail.getProduct().getSingleRoomPrice();
            this.productPrice = reservationDetail.getProduct().getPrice();
            this.detailTotalPrice = reservationDetail.getDetailTotalPrice();
        }
    }

    @ApiModel(value = "예약 상세 정보 조회 응답")
    @NoArgsConstructor
    @Getter
    @Setter
    public static class DetailInfoResDTO {

        @ApiModelProperty(value = "예약 상세 아이디")
        private Long reservationDetailId;
        @ApiModelProperty(value = "상품 아이디")
        private Long productId;
        @ApiModelProperty(value = "상품명")
        private String productName;
        @ApiModelProperty(value = "상품 썸네일")
        private String productThumbnail;
        @ApiModelProperty(value = "출발일")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;
        @ApiModelProperty(value = "도착일")
        @JsonFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;
        @ApiModelProperty(value = "예약인원")
        private int reservationPeopleNumber;
        @ApiModelProperty(value = "예약 싱글룸 개수")
        private int reservationSingleRoomNumber;
        @ApiModelProperty(value = "싱글룸 가격")
        private int productSingleRoomPrice;
        @ApiModelProperty(value = "상품 가격")
        private int productPrice;
        @ApiModelProperty(value = "예약금액")
        private int detailTotalPrice;

        public DetailInfoResDTO(ReservationDetail reservationDetail) {
            this.reservationDetailId = reservationDetail.getReservationDetailId();
            this.productId = reservationDetail.getProduct().getProductId();
            this.productName = reservationDetail.getProduct().getName();
            this.productThumbnail = reservationDetail.getProduct().getThumbnail();
            this.startDate = reservationDetail.getStartDate();
            this.endDate = reservationDetail.getEndDate();
            this.reservationPeopleNumber = reservationDetail.getNumberOfPeople();
            this.reservationSingleRoomNumber = reservationDetail.getSingleRoomNumber();
            this.productSingleRoomPrice = reservationDetail.getProduct().getSingleRoomPrice();
            this.productPrice = reservationDetail.getProduct().getPrice();
            this.detailTotalPrice = reservationDetail.getDetailTotalPrice();
        }
    }

    @ApiModel(value = "예약 추가 요청")
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AddReqDTO {

        @ApiModelProperty(value = "상품 아이디")
        private Long productId;
        @ApiModelProperty(value = "예약인원")
        private int reservationPeopleNumber;
        @ApiModelProperty(value = "예약 싱글룸 개수")
        private int reservationSingleRoomNumber;
        @ApiModelProperty(value = "상품옵션 아이디")
        private Long productOptionId;

        public ReservationDetail toEntity(Reservation reservation, Product product, ProductOption productOption, ReservationStatus reservationStatus) {
            return ReservationDetail.builder()
                    .reservation(reservation)
                    .product(product)
                    .productOptionId(productOptionId)
                    .numberOfPeople(reservationPeopleNumber)
                    .singleRoomNumber(reservationSingleRoomNumber)
                    .detailTotalPrice(product.getPrice() * reservationPeopleNumber + product.getSingleRoomPrice() * reservationSingleRoomNumber)
                    .startDate(productOption.getStartDate())
                    .endDate(productOption.getEndDate())
                    .reservationStatus(reservationStatus)
                    .build();
        }
    }
}
