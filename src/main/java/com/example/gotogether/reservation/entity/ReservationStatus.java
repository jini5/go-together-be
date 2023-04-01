package com.example.gotogether.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationStatus {
    PAYMENT_PENDING("결제 대기중"), CONFIRMED("예약 완료"), CANCEL_REQUESTED("예약 취소 요청"), CANCELLED("예약 취소"), COMPLETED("여행 완료");

    private final String value;
}
