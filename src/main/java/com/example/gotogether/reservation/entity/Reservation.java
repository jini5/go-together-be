package com.example.gotogether.reservation.entity;

import com.example.gotogether.auth.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "reservation")
public class Reservation {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;

    @Column(name="payment_method")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'UNDECIDED'")
    private PaymentMethod paymentMethod;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'UNDECIDED'")
    private ReservationStatus reservationStatus;

    @Column(name="total_amount")
    private int totalAmount;

}
