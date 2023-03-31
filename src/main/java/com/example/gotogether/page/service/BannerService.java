package com.example.gotogether.page.service;

import com.example.gotogether.page.dto.BannerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface BannerService {
    ResponseEntity<?> addBanner(BannerDTO.BannerReqDTO bannerReqDTO);

    ResponseEntity<?> deleteBanner(Long bannerId);

    @Transactional(readOnly = true)
    ResponseEntity<?>  findAllBanner();

    @Transactional(readOnly = true)
    ResponseEntity<?> findBanner(Long bannerId);

    ResponseEntity<?> updateBanner(Long bannerId, BannerDTO.BannerUpdateReqDTO bannerUpdateReqDTO);
}
