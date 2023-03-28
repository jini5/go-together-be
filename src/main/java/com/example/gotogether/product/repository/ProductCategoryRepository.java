package com.example.gotogether.product.repository;

import com.example.gotogether.category.entity.Category;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    Page<ProductCategory> findAllByCategoryIn(Pageable pageable, List<Category> categoryList);

    @Transactional
    void deleteAllByCategory(Category category);

    @Transactional
    void deleteAllByProduct(Product product);
}
