package com.example.gotogether.cart.repository;

import com.example.gotogether.auth.entity.User;
import com.example.gotogether.cart.entity.Cart;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUser(User user);
    boolean existsByUserAndProductAndProductOption(User user, Product product, ProductOption productOption);

}
