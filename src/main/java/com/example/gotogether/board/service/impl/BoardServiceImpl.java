package com.example.gotogether.board.service.impl;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.repository.UserRepository;
import com.example.gotogether.board.dto.BoardDTO;
import com.example.gotogether.board.entity.Board;
import com.example.gotogether.board.entity.BoardType;
import com.example.gotogether.board.repository.BoardRepository;
import com.example.gotogether.board.service.BoardService;
import com.example.gotogether.global.response.PageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.example.gotogether.global.config.PageSizeConfig.BOARD_LIST_SIZE;
import static com.example.gotogether.global.config.PageSizeConfig.USER_REVIEW_LIST_SIZE;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /**
     * 현재 페이지의 게시판별 게시글 목록 조회
     *
     * @param type       조회할 게시판 타입
     * @param keyword    검색할 단어 (범위: 제목)
     * @param pageNumber 현 페이지 번호
     */
    @Override
    public ResponseEntity<?> findList(BoardType type, String keyword, int pageNumber) {

        try {
            PageRequest pageRequest = PageRequest.of(pageNumber - 1, BOARD_LIST_SIZE);
            Page<Board> boardPage = boardRepository.findByTypeAndTitleContaining(type, keyword, pageRequest);

            if (boardPage == null) {
                throw new NullPointerException();
            }
            if (boardPage.getTotalElements() < 1) {

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Page<BoardDTO.ListResDTO> boardListResDTO = boardPage.map(BoardDTO.ListResDTO::new);
            if (type.equals(BoardType.NOTICE)) {
                for (BoardDTO.ListResDTO resDTO : boardListResDTO.getContent()) {
                    resDTO.setUserName("관리자");
                }
            }

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
            if (detailInfoResDTO.getBoardType().equals(BoardType.NOTICE.getValue())) {
                detailInfoResDTO.setUserName("관리자");
            }

            return new ResponseEntity<>(detailInfoResDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 게시글 추가
     *
     * @param userAccessDTO 토큰 정보
     * @param addReqDTO     추가할 게시글 정보
     */
    @Override
    public ResponseEntity<?> addPost(UserDTO.UserAccessDTO userAccessDTO, BoardDTO.AddReqDTO addReqDTO) {

        try {
            if (addReqDTO.getBoardType().equals(BoardType.NOTICE.getValue())) {
                addReqDTO.setBoardThumbnail("");
            }
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            Board board = addReqDTO.toEntity(user);
            boardRepository.save(board);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 게시글 권한 확인 (게시글 수정 및 삭제 전 선행 동작)
     *
     * @param userAccessDTO 토큰 정보
     * @param boardId       권한 확인할 게시판 아이디
     */
    @Override
    public ResponseEntity<?> checkAuthority(UserDTO.UserAccessDTO userAccessDTO, Long boardId) {

        try {
            Board board = boardRepository.findById(boardId).orElseThrow(NoSuchElementException::new);
            boolean hasAuth = hasAuthority(userAccessDTO.getEmail(), userAccessDTO.getRole(), board.getType(), board.getUser().getEmail());
            if (!hasAuth) {

                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 게시글 수정
     *
     * @param userAccessDTO 토큰 정보
     * @param modifyReqDTO  수정할 게시글 정보
     * @param boardId       수정할 게시글 아이디
     */
    @Transactional
    @Override
    public ResponseEntity<?> modifyPost(UserDTO.UserAccessDTO userAccessDTO, BoardDTO.ModifyReqDTO modifyReqDTO, Long boardId) {

        try {
            Board board = boardRepository.findById(boardId).orElseThrow(NoSuchElementException::new);
            boolean hasAuth = hasAuthority(userAccessDTO.getEmail(), userAccessDTO.getRole(), board.getType(), board.getUser().getEmail());
            if (!hasAuth) {

                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            if (board.getType().equals(BoardType.NOTICE)) {
                modifyReqDTO.setBoardThumbnail("");
            }
            board.update(modifyReqDTO);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 게시글 삭제
     *
     * @param userAccessDTO 토큰 정보
     * @param boardId       삭제할 게시글 아이디
     */
    @Override
    public ResponseEntity<?> deletePost(UserDTO.UserAccessDTO userAccessDTO, Long boardId) {

        try {
            Board board = boardRepository.findById(boardId).orElseThrow(NoSuchElementException::new);
            boolean hasAuth = hasAuthority(userAccessDTO.getEmail(), userAccessDTO.getRole(), board.getType(), board.getUser().getEmail());
            if (!hasAuth) {

                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            boardRepository.deleteById(boardId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 로그인 중인 회원의 여행후기 목록 조회
     *
     * @param userAccessDTO 토큰 정보
     * @param pageNumber    현재 페이지 번호
     */
    @Override
    public ResponseEntity<?> findMyReviewList(UserDTO.UserAccessDTO userAccessDTO, int pageNumber) {

        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            PageRequest pageRequest = PageRequest.of(pageNumber - 1, USER_REVIEW_LIST_SIZE);
            Page<Board> reviewPage = boardRepository.findByUserAndType(user, BoardType.TRAVEL_REVIEW, pageRequest);
            if (reviewPage == null) {
                throw new NullPointerException();
            }
            if (reviewPage.getTotalElements() < 1) {

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Page<BoardDTO.ReviewListResDTO> reviewListResDTO = reviewPage.map(BoardDTO.ReviewListResDTO::new);

            return new ResponseEntity<>(new PageResponseDTO(reviewListResDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 해당 게시글에 대한 로그인 중인 회원의 권한 확인
     *
     * @param userEmail        로그인 중인 회원의 이메일
     * @param userRole         로그인 중인 회원의 권한
     * @param boardType        게시글 타입
     * @param boardWriterEmail 게시글 작성자 이메일
     */
    public boolean hasAuthority(String userEmail, String userRole, BoardType boardType, String boardWriterEmail) {

        if (userRole.equals("ROLE_ADMIN")) {
            return true;
        }
        if (userRole.equals("ROLE_USER") && boardType.equals(BoardType.TRAVEL_REVIEW) && userEmail.equals(boardWriterEmail)) {
            return true;
        }
        return false;
    }
}
