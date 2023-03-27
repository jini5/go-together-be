package com.example.gotogether.page.service;

import com.example.gotogether.page.dto.RegionDTO;
import org.springframework.http.ResponseEntity;

public interface PageContentsService {
    ResponseEntity<?> addRegion(RegionDTO.RegionReqDTO dto);

    ResponseEntity<?> getRegionList();

}
