package com.example.gotogether.product.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "id")
    private Long productId;

    @OneToMany(mappedBy = "product_category")
    private List<ProductCategory> productCategory = new ArrayList<>();

    @Column(name="product_name")
    private String productName;

    @Column(name="product_summary")
    private String productSummary;

    @Column(name="product_area")
    private String productArea;

    @Column(name="product_feature")
    private String productFeature;

    @Column(name="product_airplane")
    private String productAirplane;

    @Column(name="product_single_room_price")
    private int productSingleRoomPrice;

    @Column(name="product_price")
    private int productPrice;

    @Column(name="product_type")
    private String productType;

    @Column(name="product_thumbnail")
    private String productThumbnail;

    @Column(name="product_detail")
    private String productDetail;

}
