package com.example.gotogether.auth.controller;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"회원 서비스"}, description = "회원정보수정, 회원탈퇴, 토큰 리프레시")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/myInfo")
    @ApiOperation(value = "회원정보 요청 (토큰 O)", notes = "회원정보 요청." +
            "현재 로그인회원의 정보를 반환한다.")
    public ResponseEntity<?> checkUserInfo(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO) {
        return userService.checkUserInfo(userAccessDTO);
    }

    @PatchMapping("/myInfo")
    @ApiOperation(value = "회원정보 수정 (토큰 O)", notes = "기존 비밀번호가 맞다면 DB에 저장한다.")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO, @RequestBody UserDTO.PatchUserReqDTO patchUserReqDTO) {
        return userService.updateUser(userAccessDTO, patchUserReqDTO);
    }

    @DeleteMapping("/withdraw")
    @ApiOperation(value = "회원탈퇴 버튼 (토큰 O)", notes = "기존 비밀번호가 맞다면 DB에 deleteCheck에 withdraw를 기록하고 " +
            "로그인시 deleteCheck 값이 null이 아니면 탈퇴한 회원이라는 메세지를 안내")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO, @RequestBody UserDTO.DeleteUserReqDTO deleteUserReqDTO) {
        return userService.deleteUser(userAccessDTO, deleteUserReqDTO);
    }
}
