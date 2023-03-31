package com.example.gotogether.auth.repository;

import com.example.gotogether.auth.entity.Grouping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupingRepository extends JpaRepository<Grouping, String> {
    List<Grouping> findAllByGroup(String group);
    boolean existsByGroup(String group);
}
