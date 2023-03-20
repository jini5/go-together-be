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
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @Column(name="reservation_detail_id")
    private Long reservationDetailId;

    @Column(name="number")
    private int number;

    @Column(name="single_number")
    private int singleNumber;

    @Column(name="travel_date")
    private String travelDate;

    @Column(name="detail_total_price")
    private int detailTotalPrice;
}
