package com.example.gotogether.reservation.entity;

import com.example.gotogether.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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

    @Column(name = "product_option_id")
    private Long productOptionId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_detail_id")
    private Long reservationDetailId;

    @Column(name = "number_of_people")
    private int numberOfPeople;

    @Column(name = "single_room_number")
    private int singleRoomNumber;

    @Column(name = "detail_total_price")
    private int detailTotalPrice;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;


    @Builder
    public ReservationDetail(Reservation reservation, Product product, Long productOptionId, int numberOfPeople, int singleRoomNumber, int detailTotalPrice, LocalDate startDate, LocalDate endDate, ReservationStatus reservationStatus) {
        this.reservation = reservation;
        this.product = product;
        this.productOptionId = productOptionId;
        this.numberOfPeople = numberOfPeople;
        this.singleRoomNumber = singleRoomNumber;
        this.detailTotalPrice = detailTotalPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reservationStatus = reservationStatus;
    }

    public void updateStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
