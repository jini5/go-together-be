package com.example.gotogether.board.controller;

import com.example.gotogether.board.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"게시판 서비스"}, description = "게시글 목록 조회")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @ApiOperation(value = "전체 게시글 목록 조회", notes = "해당 페이지의 게시글 목록을 조회한다.")
    @GetMapping
    public ResponseEntity<?> findAllList(@RequestParam(required = false, defaultValue = "1") int pageNumber) {
        return boardService.findAllList(pageNumber);
    }

}
