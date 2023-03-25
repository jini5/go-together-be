package com.example.gotogether.product.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    @JsonProperty("FOR_SALE")
    FOR_SALE("판매중"),
    @JsonProperty("STOP_SELLING")
    STOP_SELLING("판매중지"),
    @JsonProperty("HIDING")
    HIDING("숨김");

    private final String value;

}

