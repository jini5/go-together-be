package com.example.gotogether.product.repository;

import com.example.gotogether.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    Page<Product> searchByKeywordAndSorting(Pageable pageable, String keyword, String sort);

}
