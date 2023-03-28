package com.example.gotogether.reservation.service;

import com.example.gotogether.reservation.entity.ReservationStatus;
import org.springframework.http.ResponseEntity;

public interface ReservationService {

    ResponseEntity<?> findAllList(int pageNumber);

    ResponseEntity<?> modifyReservationStatus(Long reservationId, ReservationStatus reservationStatus);
}
