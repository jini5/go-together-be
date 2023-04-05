package com.example.gotogether.product.repository;

import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>,ProductRepositoryCustom {

    Optional<Product> findById(Long productId);

    Page<Product> findAll(Pageable pageable);

    boolean existsByName(String name);

    List<Product> findAllByTypeAndProductStatus(String type, ProductStatus status);

    List<Product> findAllByType(String type);
}