package com.example.gotogether.reservation.dto;

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

public class ReservationDTO {

    @ApiModel(value = "에약 목록 조회 응답")
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ListResDTO {

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

        public ListResDTO(Reservation reservation) {
            this.userId = reservation.getUser().getUserId();
            this.userEmail = reservation.getUser().getEmail();
            this.userName = reservation.getUser().getName();
            this.reservationId = reservation.getReservationId();
            this.reservationDate = reservation.getReservationDate();
            this.reservationStatus = reservation.getReservationStatus();
            this.paymentMethod = reservation.getPaymentMethod();
            this.totalAmount = reservation.getReservationDetails().stream()
                    .mapToInt(r -> r.getDetailTotalPrice())
                    .sum();
        }
    }
}
