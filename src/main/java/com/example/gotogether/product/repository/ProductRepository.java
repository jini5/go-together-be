package com.example.gotogether.product.repository;

import com.example.gotogether.auth.entity.User;
import com.example.gotogether.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findById(Long productId);

    Page<Product> findAll(Pageable pageable);




}
