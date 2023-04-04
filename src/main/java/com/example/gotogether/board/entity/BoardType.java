package com.example.gotogether.board.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.NoSuchElementException;

@AllArgsConstructor
@Getter
public enum BoardType {
    TRAVEL_REVIEW("여행후기"), NOTICE("알려드려요");

    private final String value;

    public static BoardType from(String type) {

        for (BoardType boardType : BoardType.values()) {
            if (boardType.value.equals(type)) {

                return boardType;
            }
        }
        throw new NoSuchElementException();
    }
}