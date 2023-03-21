package com.example.gotogether.cart.entity;

import com.example.gotogether.auth.entity.User;
import com.example.gotogether.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cart")
public class Cart {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_date")
    private TravelDate travelDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(name="number")
    private int number;

    @Column(name="single_number")
    private int singleNumber;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
