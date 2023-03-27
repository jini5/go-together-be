package com.example.gotogether.reservation.service;

import org.springframework.http.ResponseEntity;

public interface ReservationService {

    ResponseEntity<?> findAllList(int pageNumber);
}
