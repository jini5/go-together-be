package com.example.gotogether.category.entity;

import com.example.gotogether.product.entity.ProductCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long categoryId;

    @OneToMany(mappedBy = "product_category")
    private List<ProductCategory> productCategory = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Category parent;

    @Column(name = "category_depth")
    private Long categoryDepth;

    @OneToMany(mappedBy = "parent")
    private List<Category> children = new ArrayList<>();
}
