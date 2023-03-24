package com.example.gotogether.board.service.impl;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.repository.UserRepository;
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

import java.util.NoSuchElementException;

import static com.example.gotogether.global.config.PageSizeConfig.BOARD_LIST_SIZE;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

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
            Page<BoardDTO.ListResDTO> boardListResDTO = boardPage.map(BoardDTO.ListResDTO::new);

            return new ResponseEntity<>(new PageResponseDTO(boardListResDTO), HttpStatus.OK);
        } catch (NullPointerException e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 게시글 상세 정보 조회
     *
     * @param boardId 조회할 게시글 아이디
     */
    @Override
    public ResponseEntity<?> findDetailInfo(Long boardId) {

        try {
            Board board = boardRepository.findById(boardId).orElseThrow(NoSuchElementException::new);
            BoardDTO.DetailInfoResDTO detailInfoResDTO = new BoardDTO.DetailInfoResDTO(board);

            return new ResponseEntity<>(detailInfoResDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 게시글 추가
     *
     * @param userAccessDTO 토큰 정보
     * @param addReqDTO 추가할 게시글 정보
     * @return
     */
    @Override
    public ResponseEntity<?> addPost(UserDTO.UserAccessDTO userAccessDTO, BoardDTO.AddReqDTO addReqDTO) {

        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            Board board = addReqDTO.toEntity(user);
            boardRepository.save(board);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
