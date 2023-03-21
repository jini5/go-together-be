package com.example.gotogether.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "travel_date")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class TravelDate {

    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_date_id")
    private Long travelDateId;

    @Column(name="departure_date")
    private LocalDate departureDate;

    @Column(name="arrival_date")
    private LocalDate arrivalDate;

}
