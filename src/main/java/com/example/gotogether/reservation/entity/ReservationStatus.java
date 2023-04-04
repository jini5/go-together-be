package com.example.gotogether.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Getter
public enum ReservationStatus {
    PAYMENT_PENDING("결제 대기중"),
    CONFIRMED("예약 완료"),
    CANCEL_REQUESTED("예약 취소 요청"),
    CANCELLED("예약 취소"),
    COMPLETED("여행 완료");

    private final String value;

    public static ReservationStatus from(String status) {

        for (ReservationStatus reservationStatus : ReservationStatus.values()) {
            if (reservationStatus.value.equals(status)) {

                return reservationStatus;
            }
        }
        throw new NoSuchElementException();
    }
}
