package com.example.gotogether.product.repository;

import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    Optional<ProductOption> findByProductOptionId(Long ProductOptionId);

    List<ProductOption> findAllByProduct(Product product);

    @Transactional
    Optional<ProductOption> deleteAllByProductOptionId(Long ProductOptionId);

}
