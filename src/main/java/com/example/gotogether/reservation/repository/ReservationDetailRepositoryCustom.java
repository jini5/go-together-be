package com.example.gotogether.reservation.repository;

import com.example.gotogether.reservation.entity.Reservation;
import com.example.gotogether.reservation.entity.ReservationDetail;
import com.example.gotogether.reservation.entity.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationDetailRepositoryCustom {

    Page<ReservationDetail> findByUserAndReservationStatus(List<Reservation> reservationList, ReservationStatus reservationStatus, Pageable pageable);
    Page<ReservationDetail> findByUserAndNotReservationStatus(List<Reservation> reservationList, ReservationStatus reservationStatus, Pageable pageable);
}
