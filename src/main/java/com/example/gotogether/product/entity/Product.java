package com.example.gotogether.product.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductOption> productOptions = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "summary")
    private String summary;

    @Column(name = "area")
    private String area;

    @Column(name = "feature")
    private String feature;

    @Column(name = "airplane")
    private String airplane;

    @Column(name = "single_room_price")
    private int singleRoomPrice;

    @Column(name = "price")
    private int price;

    @Column(name = "type")
    private String type;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "detail")
    private String detail;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'FOR_SALE'")
    private ProductStatus productStatus;

}
