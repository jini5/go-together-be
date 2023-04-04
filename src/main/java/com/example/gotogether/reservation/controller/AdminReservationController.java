package com.example.gotogether.reservation.controller;

import com.example.gotogether.reservation.dto.ReservationDTO;
import com.example.gotogether.reservation.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"관리자 예약 서비스"}, description = "전체 예약 목록 조회, 예약 상태 수정")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/admin/reservations")
public class AdminReservationController {

    private final ReservationService reservationService;

    @ApiOperation(value = "전체 예약 목록 조회", notes = "현 페이지의 예약 목록을 조회한다.\n\n" +
            "code: 200 조회 성공, 204 조회 성공 + 표시할 내용 없음, 500 알 수 없는 서버 오류")
    @GetMapping
    public ResponseEntity<?> findAllList(@RequestParam(required = false, defaultValue = "1") int pageNumber) {
        return reservationService.findAllList(pageNumber);
    }

    @ApiOperation(value = "예약상태 수정", notes = "예약 상태를 수정한다.\n\n" +
            "code: 200 수정 성공, 400 잘못된 reservationDetailId 또는 ReservationStatus 요청")
    @PatchMapping("/{reservationDetailId}")
    public ResponseEntity<?> modifyReservationStatus(@PathVariable Long reservationDetailId,
                                                     @RequestBody ReservationDTO.ModifyStatusReqDTO modifyStatusReqDTO) {
        return reservationService.modifyReservationStatus(reservationDetailId, modifyStatusReqDTO);
    }
}
