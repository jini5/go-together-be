package com.example.gotogether.page.service;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.page.dto.RegionDTO;
import org.springframework.http.ResponseEntity;

public interface PageContentsService {
    ResponseEntity<?> addRegion(RegionDTO.RegionReqDTO dto);

    ResponseEntity<?> getRegionList();

    ResponseEntity<?> updateRegion(Long regionId,RegionDTO.RegionUpdateReqDTO dto);
    ResponseEntity<?> deleteRegion(Long regionId);

    ResponseEntity<?> getRegionDetail(Long regionId);
    ResponseEntity<?> findGroupProduct(UserDTO.UserAccessDTO userAccessDTO);
}
