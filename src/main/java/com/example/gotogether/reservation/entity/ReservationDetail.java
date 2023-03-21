package com.example.gotogether.reservation.entity;

import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.TravelDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reservation_detail")
public class ReservationDetail {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_date")
    private TravelDate travelDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_detail_id")
    private Long reservationDetailId;

    @Column(name="number")
    private int number;

    @Column(name="single_number")
    private int singleNumber;

    @Column(name="detail_total_price")
    private int detailTotalPrice;
}
