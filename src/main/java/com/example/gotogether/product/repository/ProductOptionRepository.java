package com.example.gotogether.product.repository;

import com.example.gotogether.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    Optional<ProductOption> findByProductOptionId(Long ProductOptionId);

    Optional<ProductOption> deleteAllBy(Long ProductOptionId);

}
