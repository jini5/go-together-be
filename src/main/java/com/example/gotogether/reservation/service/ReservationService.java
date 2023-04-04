package com.example.gotogether.reservation.service;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.reservation.dto.ReservationDTO;
import org.springframework.http.ResponseEntity;

public interface ReservationService {

    ResponseEntity<?> findAllList(int page);

    ResponseEntity<?> modifyReservationStatus(Long reservationId, ReservationDTO.ModifyStatusReqDTO modifyStatusReqDTO);

    ResponseEntity<?> findList(UserDTO.UserAccessDTO userAccessDTO, int pageNumber);

    ResponseEntity<?> findDetail(UserDTO.UserAccessDTO userAccessDTO, Long reservationDetailId);

    ResponseEntity<?> cancelReservation(UserDTO.UserAccessDTO userAccessDTO, Long reservationDetailId);

    ResponseEntity<?> addReservation(UserDTO.UserAccessDTO userAccessDTO, ReservationDTO.AddReqDTO addReqDTO);
}
