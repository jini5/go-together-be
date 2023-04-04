package com.example.gotogether.cart.entity;

import com.example.gotogether.auth.entity.User;
import com.example.gotogether.cart.dto.CartDTO;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
@Table(name = "cart")
public class Cart {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(name = "number_of_people")
    private int numberOfPeople;

    @Column(name = "single_room_number")
    private int singleRoomNumber;


    @Builder
    public Cart(User user, Product product, ProductOption productOption, Long cartId, int numberOfPeople, int singleRoomNumber) {
        this.user = user;
        this.product = product;
        this.productOption = productOption;
        this.cartId = cartId;
        this.numberOfPeople = numberOfPeople;
        this.singleRoomNumber = singleRoomNumber;
    }

    public void update(CartDTO.UpdateCartReqDTO updateCartReqDTO, ProductOption productOption) {
        this.productOption = productOption;
        this.numberOfPeople = updateCartReqDTO.getNumberOfPeople();
        this.singleRoomNumber = updateCartReqDTO.getSingleRoomNumber();
    }
}
