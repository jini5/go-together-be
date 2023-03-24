package com.example.gotogether.product.entity;

import com.example.gotogether.category.entity.Category;
import lombok.*;


import javax.persistence.*;

@Entity
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

    @Builder
    public ProductCategory(Product product, Category category) {
        this.product = product;
        this.category = category;
    }
}
