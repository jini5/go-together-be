package com.example.gotogether.product.entity;

import com.example.gotogether.auth.entity.User;
import com.example.gotogether.category.entity.Category;
import lombok.*;


import javax.persistence.*;

@Entity
@Table(name = "product_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @Column(name="product_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


}
