package com.example.gotogether.board.service;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.board.dto.BoardDTO;
import com.example.gotogether.board.entity.BoardType;
import org.springframework.http.ResponseEntity;

public interface BoardService {

    ResponseEntity<?> findList(BoardType type, int pageNumber);

    ResponseEntity<?> findDetailInfo(Long boardId);

    ResponseEntity<?> addPost(UserDTO.UserAccessDTO userAccessDTO, BoardDTO.AddReqDTO addReqDTO);

    ResponseEntity<?> checkAuthority(UserDTO.UserAccessDTO userAccessDTO, Long boardId);

    ResponseEntity<?> modifyPost(UserDTO.UserAccessDTO userAccessDTO, BoardDTO.ModifyReqDTO modifyReqDTO, Long boardId);

    ResponseEntity<?> deletePost(UserDTO.UserAccessDTO userAccessDTO, Long boardId);

    ResponseEntity<?> searchPost(BoardType type, String keyword, int pageNumber);

    ResponseEntity<?> findMyReviewList(UserDTO.UserAccessDTO userAccessDTO, int pageNumber);
}
