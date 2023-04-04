package com.example.gotogether.reservation.controller;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.reservation.dto.ReservationDTO;
import com.example.gotogether.reservation.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"사용자 예약 서비스"}, description = "회원 예약 목록 조회, 예약 상세 정보 조회, 회원 예약 취소, 회원 예약 추가")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @ApiOperation(value = "회원 예약 목록 조회", notes = "현 페이지의, 로그인 중인 회원의 예약 목록을 조회한다.\n\n" +
            "code: 200 조회 성공, 204 조회 성공 + 표시할 내용 없음, 403 권한없는 사용자 접근, 500 알 수 없는 서버 오류")
    @GetMapping
    public ResponseEntity<?> findList(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO,
                                      @RequestParam(required = false, defaultValue = "1") int pageNumber) {
        return reservationService.findList(userAccessDTO, pageNumber);
    }

    @ApiOperation(value = "예약 상세 정보 조회", notes = "예약 상세 정보를 조회한다.\n\n"
            + "code: 200 조회 성공, 400 잘못된 reservationId 요청, 403 권한없는 사용자 접근")
    @GetMapping("/{reservationDetailId}")
    public ResponseEntity<?> findDetail(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO,
                                        @PathVariable Long reservationDetailId) {
        return reservationService.findDetail(userAccessDTO, reservationDetailId);
    }

    @ApiOperation(value = "회원 예약 취소", notes = "회원의 예약을 취소한다.\n\n" +
            "code: 200 예약 취소 성공, 400 (1) 잘못된 reservationDetailId 요청, 400 (2) 잘못된 예약상태를 가진 예약 요청," +
            " 403 권한없는 사용자 접근")
    @DeleteMapping("/{reservationDetailId}")
    public ResponseEntity<?> cancelReservation(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO,
                                               @PathVariable Long reservationDetailId) {
        return reservationService.cancelReservation(userAccessDTO, reservationDetailId);
    }

    @ApiOperation(value = "회원 예약 추가", notes = "회원의 예약을 추가한다.\n\n" +
            "code: 201 예약 추가 성공, 400 (1) 잘못된 user 토큰 정보 또는 productId 또는 productOptionId 요청, " +
            "400 (2) 최대값 이상의 reservationPeopleNumber 또는 reservationSingleRoomNumber 요청, " +
            "400 (3) 잘못된 paymentMethod 요청")
    @PostMapping
    public ResponseEntity<?> addReservation(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO,
                                            @RequestBody ReservationDTO.AddReqDTO addReqDTO) {
        return reservationService.addReservation(userAccessDTO, addReqDTO);
    }
}
