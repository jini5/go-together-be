package com.example.gotogether.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationStatus {
    PENDING("주문 접수"), CONFIRMED("예약 완료"), PAYMENT_PENDING("결제 대기중"), PAYMENT_COMPLETE("결제 완료"), CANCEL_REQUESTED("예약 취소 요청"), CANCELLED("예약 취소"), COMPLETED("여행 완료"), UNDECIDED("미정");

    private final String value;
}
