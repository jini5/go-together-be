package com.example.gotogether.page.service.impl;

import com.example.gotogether.page.dto.RegionDTO;
import com.example.gotogether.page.entity.Region;
import com.example.gotogether.page.repository.RegionRepository;
import com.example.gotogether.page.service.PageContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageContentsServiceImpl implements PageContentsService {
    private final RegionRepository regionRepository;


    @Override
    public ResponseEntity<?> addRegion(RegionDTO.RegionReqDTO dto) {
        if(regionRepository.existsByRegion(dto.getRegionName())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        regionRepository.save(dto.toEntity());
        return new  ResponseEntity<>(HttpStatus.CREATED);
    }

}
