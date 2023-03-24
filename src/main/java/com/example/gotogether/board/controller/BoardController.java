package com.example.gotogether.board.controller;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.board.dto.BoardDTO;
import com.example.gotogether.board.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"게시판 서비스"}, description = "게시글 목록 조회")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @ApiOperation(value = "전체 게시글 목록 조회", notes = "해당 페이지의 게시글 목록을 조회한다.\n" +
            "code: 200 조회 성공, 204 조회 성공 + 표시할 내용 없음, 500 알 수 없는 서버 오류")
    @GetMapping
    public ResponseEntity<?> findAllList(@RequestParam(required = false, defaultValue = "1") int pageNumber) {
        return boardService.findAllList(pageNumber);
    }

    @ApiOperation(value = "게시글 상세 정보 조회", notes = "게시글 상세 정보를 조회한다.\n" +
            "code: 200 조회 성공, 400 잘못된 boardId 요청")
    @GetMapping("/detail/{boardId}")
    public ResponseEntity<?> findDetailInfo(@PathVariable Long boardId) {
        return boardService.findDetailInfo(boardId);
    }

    @ApiOperation(value = "게시글 추가", notes = "게시글을 추가한다.\n" +
            "code: 201 추가 성공, 401 없는 사용자 또는 잘못된 요청")
    @PostMapping
    public ResponseEntity<?> addPost(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO, @RequestBody BoardDTO.AddReqDTO addReqDTO) {
        return boardService.addPost(userAccessDTO, addReqDTO);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.\n" +
            "code: 200 수정 성공, 400 잘못된 boardId 요청, 403 권한없는 사용자 접근")
    @PatchMapping("/{boardId}")
    public ResponseEntity<?> modifyPost(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO,
                                        @RequestBody BoardDTO.ModifyReqDTO modifyReqDTO, @PathVariable Long boardId) {
        return boardService.modifyPost(userAccessDTO, modifyReqDTO, boardId);
    }

    @ApiOperation(value = "게시글 권한 확인", notes = "게시글에 대한 사용자의 권한을 확인한다.\n" +
            "code: 200 올바른 권한 확인 성공, 400 잘못된 boardId 요청, 403 권한없는 사용자 접근")
    @GetMapping("/authority/{boardId}")
    public ResponseEntity<?> checkAuthority(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO, @PathVariable Long boardId) {
        return boardService.checkAuthority(userAccessDTO, boardId);
    }
}
