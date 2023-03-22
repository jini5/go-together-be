package com.example.gotogether.reservation.entity;

import com.example.gotogether.auth.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "reservation")
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @Column(name = "reservation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @OneToMany(mappedBy = "reservation",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<ReservationDetail> reservationDetails = new ArrayList<>();

    @CreatedDate
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
