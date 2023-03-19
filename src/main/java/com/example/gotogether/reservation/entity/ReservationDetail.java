package com.example.gotogether.reservation.entity;

import com.example.gotogether.auth.entity.User;
import com.example.gotogether.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "reservation_detail")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class ReservationDetail {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Product product;

    @Id
    @Column(name="id")
    private Long reservationDetailId;

    @Column(name="reservation_number")
    private int reservationNumber;

    @Column(name="reservation_single_number")
    private int reservationSingleNumber;

    @Column(name="reservation_travel_date")
    private String reservationrTravelDate;

    @Column(name="reservation_detail_total_price")
    private int reservationDetailTotalPrice;
}
