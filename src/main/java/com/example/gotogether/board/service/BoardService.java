package com.example.gotogether.board.service;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.board.dto.BoardDTO;
import org.springframework.http.ResponseEntity;

public interface BoardService {

    ResponseEntity<?> findAllList(int pageNumber);

    ResponseEntity<?> findDetailInfo(Long boardId);

    ResponseEntity<?> addPost(UserDTO.UserAccessDTO userAccessDTO, BoardDTO.AddReqDTO addReqDTO);
}
