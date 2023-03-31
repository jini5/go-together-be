package com.example.gotogether.board.config;

import com.example.gotogether.board.entity.BoardType;
import org.springframework.core.convert.converter.Converter;

public class BoardTypeRequestConverter implements Converter<String, BoardType> {

    @Override
    public BoardType convert(String type) {
        return BoardType.from(type);
    }
}
