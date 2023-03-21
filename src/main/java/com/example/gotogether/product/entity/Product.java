package com.example.gotogether.product.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @OneToMany(mappedBy = "product_category")
    private List<ProductCategory> productCategory = new ArrayList<>();

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

    @Column(name="type")
    private String type;

    @Column(name="thumbnail")
    private String thumbnail;

    @Column(name="detail")
    private String detail;

}
