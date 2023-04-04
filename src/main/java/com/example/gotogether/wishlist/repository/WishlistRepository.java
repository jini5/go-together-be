package com.example.gotogether.wishlist.repository;

import com.example.gotogether.auth.entity.User;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findAllByUser(User user);

    Boolean existsByUserAndProduct(User user, Product product);

    void deleteByUserAndWishlistId(User user, Long wishlistId);

    boolean existsByUserAndWishlistId(User user, Long wishlistId);

}
