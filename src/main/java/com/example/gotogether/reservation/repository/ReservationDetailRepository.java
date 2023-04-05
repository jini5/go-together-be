package com.example.gotogether.reservation.repository;

import com.example.gotogether.reservation.entity.ReservationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationDetailRepository extends JpaRepository<ReservationDetail, Long>, ReservationDetailRepositoryCustom {
}
