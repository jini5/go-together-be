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
    @ApiOperation(value = "회원정보 요청 (토큰 O)", notes = "회원정보 요청. 현재 로그인회원의 정보를 반환한다.\n" +
            "code: 200 사용자 정보 반환 성공, 400 없는 회원 또는 잘못된 요청")
    public ResponseEntity<?> checkUserInfo(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO) {
        return userService.checkUserInfo(userAccessDTO);
    }

    @PatchMapping("/myInfo")
    @ApiOperation(value = "회원정보 수정 (토큰 O)", notes = "기존 비밀번호가 맞다면 DB에 저장한다.\n" +
            "code: 200 수정 성공, 400 비밀번호 불일치, 401 없는 사용자 또는 잘못된 요청")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO, @RequestBody UserDTO.PatchUserReqDTO patchUserReqDTO) {
        return userService.updateUser(userAccessDTO, patchUserReqDTO);
    }

    @DeleteMapping("/withdraw")
    @ApiOperation(value = "회원탈퇴 버튼 (토큰 O)", notes = "기존 비밀번호가 맞다면 DB의 deleteCheck 값 변경.\n" +
            "code: 200 탈퇴 성공, 401 잘못된 사용자 또는 비밀번호 불일치")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO, @RequestBody UserDTO.DeleteUserReqDTO deleteUserReqDTO) {
        return userService.deleteUser(userAccessDTO, deleteUserReqDTO);
    }

    @PatchMapping("/type")
    @ApiOperation(value = "사용자 여행 유형 저장 또는 수정", notes = "기존 유형이 없다면 저장, 있다면 수정.\n" +
            "code: 200 저장됨, 404 잘못된 사용자")
    public ResponseEntity<?> saveUserType(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO,@RequestBody UserDTO.UserType userType){
       return userService.saveUserType(userAccessDTO,userType);
    }

}
