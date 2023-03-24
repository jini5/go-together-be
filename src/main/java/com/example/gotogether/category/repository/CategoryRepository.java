package com.example.gotogether.category.repository;

import com.example.gotogether.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByParentIsNull();

    boolean existsByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM category as c WHERE c.category_id IN (:id_list)")
    List<Category> findAllByCategoryId(@Param("id_list") List<Long> id_list);
}