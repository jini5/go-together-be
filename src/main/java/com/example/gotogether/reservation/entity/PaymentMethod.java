package com.example.gotogether.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
    NON_BANK_ACCOUNT("무통장 입금"), BANK_TRANSFER("실시간 계좌이체"), UNDECIDED("미정");

    private final String value;
}
