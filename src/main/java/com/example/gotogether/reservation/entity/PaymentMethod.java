package com.example.gotogether.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
    NON_BANK_ACCOUNT("무통장 입금"), BANK_TRANSFER("실시간 계좌이체");

    private final String value;

    public static PaymentMethod from(String method) {

        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.value.equals(method)) {

                return paymentMethod;
            }
        }
        throw new NoSuchElementException();
    }
}
