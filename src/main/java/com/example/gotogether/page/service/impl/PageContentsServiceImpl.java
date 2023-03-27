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

    @Override
    public ResponseEntity<?> getRegionList() {
        Sort sort = Sort.by(Sort.Direction.ASC,"rate");
        List<Region> regionList = regionRepository.findAll(sort);
        if (regionList.size()<1){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(regionList.stream().map(RegionDTO.RegionResDTO::new).collect(Collectors.toList()),HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateRegion(Long regionId,RegionDTO.RegionUpdateReqDTO dto) {
        try {
            Region region = regionRepository.findById(regionId).orElseThrow(IllegalArgumentException::new);
            region.update(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteRegion(Long regionId) {
        try {
            Region region = regionRepository.findById(regionId).orElseThrow(IllegalArgumentException::new);
            regionRepository.delete(region);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
