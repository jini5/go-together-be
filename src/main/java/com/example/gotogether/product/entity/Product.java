package com.example.gotogether.product.entity;

import com.example.gotogether.product.dto.ProductDTO;
import lombok.AccessLevel;
import lombok.Builder;
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

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductCategory> categories = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    public void changeStatusHiding(Product product) {
        product.productStatus = productStatus.HIDING;
    }

    @Builder
    public Product(Long productId, String name, String summary, String area, String feature, String airplane, int singleRoomPrice, int price, String type, String thumbnail, String detail, ProductStatus productStatus) {
        this.productId = productId;
        this.name = name;
        this.summary = summary;
        this.area = area;
        this.feature = feature;
        this.airplane = airplane;
        this.singleRoomPrice = singleRoomPrice;
        this.price = price;
        this.type = type;
        this.thumbnail = thumbnail;
        this.detail = detail;
        this.productStatus = productStatus;
    }


    public void update(ProductDTO.ProductReqDTO productReq,List<ProductCategory> categoryList) {
        this.categories = categoryList;
        this.name = productReq.getName();
        this.summary = productReq.getSummary();
        this.area = productReq.getArea();
        this.feature = productReq.getFeature();
        this.airplane = productReq.getAirplane();
        this.singleRoomPrice = productReq.getSingleRoomPrice();
        this.price = productReq.getPrice();
        this.type = productReq.getType();
        this.thumbnail = productReq.getThumbnail();
        this.detail = productReq.getDetail();
        this.productStatus = productReq.getProductStatus();
    }


}
