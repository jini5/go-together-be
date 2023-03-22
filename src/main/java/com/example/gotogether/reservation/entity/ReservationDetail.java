package com.example.gotogether.reservation.entity;

import com.example.gotogether.product.entity.Product;
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

    @Column(name = "product_data_option_id")
    private Long productDateOptionId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_detail_id")
    private Long reservationDetailId;

    @Column(name="number_of_people")
    private int numberOfPeople;

    @Column(name="single_room_number")
    private int singleRoomNumber;

    @Column(name="detail_total_price")
    private int detailTotalPrice;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    private LocalDate endDate;

}
