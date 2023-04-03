package com.example.gotogether.reservation.dto;

import com.example.gotogether.auth.entity.User;
import com.example.gotogether.reservation.entity.PaymentMethod;
import com.example.gotogether.reservation.entity.Reservation;
import com.example.gotogether.reservation.entity.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationDTO {

    @ApiModel(value = "관리자 페이지 에약 목록 조회 응답")
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AdminListResDTO {

        @ApiModelProperty(value = "예약자 아이디")
        private Long userId;
        @ApiModelProperty(value = "예약자 이메일")
        private String userEmail;
        @ApiModelProperty(value = "예약자 이름")
        private String userName;
        @ApiModelProperty(value = "예약 아이디")
        private Long reservationId;
        @ApiModelProperty(value = "예약일")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime reservationDate;
        @ApiModelProperty(value = "예약 상태")
        private ReservationStatus reservationStatus;
        @ApiModelProperty(value = "결제 수단")
        private PaymentMethod paymentMethod;
        @ApiModelProperty(value = "총 예약 금액")
        private int totalAmount;

        public AdminListResDTO(Reservation reservation) {
            this.userId = reservation.getUser().getUserId();
            this.userEmail = reservation.getUser().getEmail();
            this.userName = reservation.getUser().getName();
            this.reservationId = reservation.getReservationId();
            this.reservationDate = reservation.getReservationDate();
            this.paymentMethod = reservation.getPaymentMethod();
            this.totalAmount = reservation.getReservationDetails().stream()
                    .mapToInt(r -> r.getDetailTotalPrice())
                    .sum();
        }
    }

    @ApiModel(value = "예약상태 수정")
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ModifyStatusReqDTO {

        @ApiModelProperty(value = "예약상태")
        private ReservationStatus reservationStatus;
    }

    @ApiModel(value = "사용자 페이지 예약 목록 조회 응답")
    @NoArgsConstructor
    @Getter
    @Setter
    public static class UserListResDTO {

        @ApiModelProperty(value = "예약 아이디")
        private Long reservationId;
        @ApiModelProperty(value = "결제수단")
        private PaymentMethod paymentMethod;
        @ApiModelProperty(value = "총 예약금액")
        private int totalAmount;
        @ApiModelProperty(value = "예약상태")
        private ReservationStatus reservationStatus;
        @ApiModelProperty(value = "예약일")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime reservationDate;
        @ApiModelProperty(value = "예약상품리스트")
        List<ReservationDetailDTO.UserListResDTO> reservationProductList;

        public UserListResDTO(Reservation reservation) {
            this.reservationId = reservation.getReservationId();
            this.paymentMethod = reservation.getPaymentMethod();
            this.totalAmount = reservation.getReservationDetails().stream()
                    .mapToInt(r -> r.getDetailTotalPrice())
                    .sum();
            this.reservationDate = reservation.getReservationDate();
            this.reservationProductList = reservation.getReservationDetails().stream()
                    .map(ReservationDetailDTO.UserListResDTO::new)
                    .collect(Collectors.toList());
        }
    }

    @ApiModel(value = "예약 상세 정보 조회 응답")
    @NoArgsConstructor
    @Getter
    @Setter
    public static class DetailInfoResDTO {

        @ApiModelProperty(value = "예약자 아이디")
        private Long userId;
        @ApiModelProperty(value = "예약자 이메일")
        private String userEmail;
        @ApiModelProperty(value = "예약자 이름")
        private String userName;
        @ApiModelProperty(value = "예약 아이디")
        private Long reservationId;
        @ApiModelProperty(value = "예약일")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime reservationDate;
        @ApiModelProperty(value = "예약상태")
        private ReservationStatus reservationStatus;
        @ApiModelProperty(value = "결제수단")
        private PaymentMethod paymentMethod;
        @ApiModelProperty(value = "총 예약금액")
        private int totalAmount;
        @ApiModelProperty(value = "예약상품리스트")
        private List<ReservationDetailDTO.DetailInfoResDTO> reservationProductList;

        public DetailInfoResDTO(Reservation reservation) {
            this.userId = reservation.getUser().getUserId();
            this.userEmail = reservation.getUser().getEmail();
            this.userName = reservation.getUser().getName();
            this.reservationId = reservation.getReservationId();
            this.reservationDate = reservation.getReservationDate();
            this.paymentMethod = reservation.getPaymentMethod();
            this.totalAmount = reservation.getReservationDetails().stream()
                    .mapToInt(r -> r.getDetailTotalPrice())
                    .sum();
            this.reservationProductList = reservation.getReservationDetails().stream()
                    .map(ReservationDetailDTO.DetailInfoResDTO::new)
                    .collect(Collectors.toList());
        }
    }

    @ApiModel(value = "예약 추가 요청")
    @NoArgsConstructor
    @Getter
    @Setter
    public static class AddReqDTO {

        @ApiModelProperty(value = "결제수단")
        private String paymentMethod;
        @ApiModelProperty(value = "예약리스트")
        private List<ReservationDetailDTO.AddReqDTO> reservationList;

        public Reservation toEntity(User user) {
            return Reservation.builder()
                    .user(user)
                    .paymentMethod(PaymentMethod.from(paymentMethod))
                    .build();
        }
    }
}
