package com.example.gotogether.board.service.impl;

import com.example.gotogether.board.dto.BoardDTO;
import com.example.gotogether.board.entity.Board;
import com.example.gotogether.board.repository.BoardRepository;
import com.example.gotogether.board.service.BoardService;
import com.example.gotogether.global.response.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.example.gotogether.global.config.PageSizeConfig.BOARD_LIST_SIZE;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    /**
     * 현 페이지의 게시글 목록 조회
     *
     * @param pageNumber 현 페이지 번호
     */
    @Override
    public ResponseEntity<?> findAllList(int pageNumber) {
        try {
            PageRequest pageRequest = PageRequest.of(pageNumber - 1, BOARD_LIST_SIZE);
            Page<Board> boardPage = boardRepository.findAll(pageRequest);
            if (boardPage == null) {
                throw new NullPointerException();
            }
            if (boardPage.getTotalElements() < 1) {

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Page<BoardDTO.BoardListResDTO> boardListResDTO = boardPage.map(BoardDTO.BoardListResDTO::new);

            return new ResponseEntity<>(new PageResponseDTO(boardListResDTO), HttpStatus.OK);
        } catch (NullPointerException e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
