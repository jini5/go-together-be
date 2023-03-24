package com.example.gotogether.product.repository;

import com.example.gotogether.category.entity.Category;
import com.example.gotogether.product.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Page<ProductCategory> findAllByCategoryIn(Pageable pageable, List<Category> categoryList);
}
