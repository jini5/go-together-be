package com.example.gotogether.product.repository;

import com.example.gotogether.category.entity.Category;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Transactional
    void deleteAllByCategory(Category category);

    @Transactional
    void deleteAllByProduct(Product product);
}
