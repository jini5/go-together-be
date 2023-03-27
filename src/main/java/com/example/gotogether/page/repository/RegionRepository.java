package com.example.gotogether.page.repository;

import com.example.gotogether.page.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {

    boolean existsByRegion(String region);


}
