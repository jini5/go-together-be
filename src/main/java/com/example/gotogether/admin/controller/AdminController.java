package com.example.gotogether.admin.controller;

import com.example.gotogether.admin.service.AdminService;
import com.example.gotogether.auth.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.gotogether.global.config.PageSizeConfig.User_List_Size;

@Api(tags = {"관리자 서비스"}, description = "관리자 권한 부여,")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PatchMapping("/setAdmin/{email}")
    @ApiOperation(value = "관리자 권한 부여", notes = "입력받은 email 을 통해 관리자 권한 부여")
    public ResponseEntity<?> setAdmin(@PathVariable String email) {
        return adminService.setUserToAdmin(email);
    }

    @PatchMapping("/deprivation/{email}")
    @ApiOperation(value = "관리자 권한 박탈", notes = "입력받은 email 을 통해 관리자 권한 박탈")
    public ResponseEntity<?> setUser(@PathVariable String email) {
        return adminService.setAdminToUser(email);
    }

    @GetMapping("/userList")
    @ApiOperation(value = "회원 리스트 조회", notes = "관리자가 회원 목록 20명씩 조회")
    public ResponseEntity<?> findUserList(@RequestParam(required = false, defaultValue = "1") String page) {
        PageRequest pageRequest = null;
        try {
            int intPage = Integer.parseInt(page);
            pageRequest = PageRequest.of(intPage - 1, User_List_Size);
        } catch (IllegalArgumentException e) {
            pageRequest = PageRequest.of(0, User_List_Size);
        }
        return adminService.findUserList(pageRequest);
    }


}
