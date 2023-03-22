package com.example.gotogether.product.entity;

import com.example.gotogether.reservation.entity.ReservationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductCategory> categories = new ArrayList<>();

    @Column(name="name")
    private String name;

    @Column(name="summary")
    private String summary;

    @Column(name="area")
    private String area;

    @Column(name="feature")
    private String feature;

    @Column(name="airplane")
    private String airplane;

    @Column(name="single_room_price")
    private int singleRoomPrice;

    @Column(name="price")
    private int price;

    @Column(name="travel_date_list")
    @Convert(converter = StringListConverter.class)
    private List<String> travelDateList;

    @Column(name="type")
    private String type;

    @Column(name="thumbnail")
    private String thumbnail;

    @Column(name="detail")
    private String detail;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'FOR_SALE'")
    private ProductStatus productStatus;

}
