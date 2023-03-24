package com.example.gotogether.board.service;

import org.springframework.http.ResponseEntity;

public interface BoardService {

    ResponseEntity<?> findAllList(int pageNumber);

}
