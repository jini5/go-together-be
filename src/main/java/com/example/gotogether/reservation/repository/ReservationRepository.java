package com.example.gotogether.reservation.repository;

import com.example.gotogether.auth.entity.User;
import com.example.gotogether.reservation.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAll(Pageable pageable);
    List<Reservation> findByUser(User user);
}
