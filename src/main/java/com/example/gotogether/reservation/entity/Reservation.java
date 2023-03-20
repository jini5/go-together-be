package com.example.gotogether.reservation.entity;

import com.example.gotogether.auth.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Reservation {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

    @Column(name="payment_method")
    private String paymentMethod;

    @Column(name="status")
    private String status;

    @Column(name="total_amount")
    private int totalAmount;

}
