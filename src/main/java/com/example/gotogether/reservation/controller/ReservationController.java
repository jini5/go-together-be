package com.example.gotogether.reservation.controller;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.reservation.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"사용자 예약 서비스"}, description = "회원 예약 목록 조회, 예약 상세 정보 조회")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @ApiOperation(value = "회원 예약 목록 조회", notes = "현 페이지의, 로그인 중인 회원의 예약 목록을 조회한다.\n\n" +
            "code: 200 조회 성공, 204 조회 성공 + 표시할 내용 없음, 403 권한없는 사용자 접근, 500 알 수 없는 서버 오류")
    @GetMapping
    public ResponseEntity<?> findList(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO, @RequestParam(required = false, defaultValue = "1") int pageNumber) {
        return reservationService.findList(userAccessDTO, pageNumber);
    }

    @ApiOperation(value = "예약 상세 정보 조회", notes = "예약 상세 정보를 조회한다.\n\n"
            + "code: 200 조회 성공, 400 잘못된 reservationId 요청, 403 권한없는 사용자 접근")
    @GetMapping("/{reservationId}")
    public ResponseEntity<?> findDetailInfo(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO, @PathVariable Long reservationId) {
        return reservationService.findDetailInfo(userAccessDTO, reservationId);
    }
}
