package com.example.gotogether.page.entity;

import com.example.gotogether.page.dto.BannerDTO;
import com.example.gotogether.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "banner")
public class Banner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private Long bannerId;

    @Column(name = "tag")
    private String tag;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Banner(String image,String tag, String title, String subtitle) {
        this.image = image;
        this.tag = tag;
        this.title = title;
        this.subtitle = subtitle;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void update(BannerDTO.BannerUpdateReqDTO bannerUpdateReqDTO, Product product) {
        this.image = bannerUpdateReqDTO.getImage();
        this.product = product;
        this.tag = bannerUpdateReqDTO.getTag();
        this.title = bannerUpdateReqDTO.getTitle();
        this.subtitle = bannerUpdateReqDTO.getSubtitle();
    }
}
