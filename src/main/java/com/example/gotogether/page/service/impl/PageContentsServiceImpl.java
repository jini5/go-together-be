package com.example.gotogether.page.service.impl;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.repository.UserRepository;
import com.example.gotogether.page.dto.RegionDTO;
import com.example.gotogether.page.entity.Region;
import com.example.gotogether.page.repository.RegionRepository;
import com.example.gotogether.page.service.PageContentsService;
import com.example.gotogether.product.dto.ProductDTO;
import com.example.gotogether.product.entity.ProductStatus;
import com.example.gotogether.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PageContentsServiceImpl implements PageContentsService {
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Override
    public ResponseEntity<?> addRegion(RegionDTO.RegionReqDTO dto) {
        if (regionRepository.existsByRegionName(dto.getRegionName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        regionRepository.save(dto.toEntity());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> getRegionList() {
        Sort sort = Sort.by(Sort.Direction.ASC, "rate");
        List<Region> regionList = regionRepository.findAll(sort);
        if (regionList.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(regionList.stream().map(RegionDTO.RegionResDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateRegion(Long regionId, RegionDTO.RegionUpdateReqDTO dto) {
        try {
            Region region = regionRepository.findById(regionId).orElseThrow(IllegalArgumentException::new);
            if (!region.getRegionName().equals(dto.getRegionName()) && regionRepository.existsByRegionName(dto.getRegionName())) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            region.update(dto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteRegion(Long regionId) {
        try {
            Region region = regionRepository.findById(regionId).orElseThrow(IllegalArgumentException::new);
            regionRepository.delete(region);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> getRegionDetail(Long regionId) {
        try {
            Region region = regionRepository.findById(regionId).orElseThrow(NoSuchElementException::new);
            return new ResponseEntity<>(new RegionDTO.RegionResDTO(region), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> findGroupProduct(UserDTO.UserAccessDTO userAccessDTO) {
        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            if (user.getType() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            List<ProductDTO.ProductListResDTO> list = productRepository.findAllByTypeAndProductStatus(user.getType().getGroup(), ProductStatus.FOR_SALE)
                    .stream()
                    .map(ProductDTO.ProductListResDTO::new)
                    .collect(Collectors.toList());
            if (list.size() < 1) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
