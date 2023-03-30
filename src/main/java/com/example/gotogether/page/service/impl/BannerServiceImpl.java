package com.example.gotogether.page.service.impl;

import com.example.gotogether.page.dto.BannerDTO;
import com.example.gotogether.page.entity.Banner;
import com.example.gotogether.page.repository.BannerRepository;
import com.example.gotogether.page.service.BannerService;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    private final ProductRepository productRepository;


    @Override
    public ResponseEntity<?> addBanner(BannerDTO.BannerReqDTO bannerReqDTO) {
        try{
            Product product = productRepository.findById(bannerReqDTO.getProductId()).orElseThrow(NoSuchElementException::new);
            Banner banner = bannerReqDTO.toEntity();
            banner.setProduct(product);
            bannerRepository.save(banner);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public ResponseEntity<?> deleteBanner(Long bannerId) {
        try{
            bannerRepository.deleteById(bannerId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> updateBanner(Long bannerId, BannerDTO.BannerUpdateReqDTO bannerUpdateReqDTO) {
        try{
            Banner banner = bannerRepository.findById(bannerId).orElseThrow(NoSuchElementException::new);
            Product product = productRepository.findById(bannerUpdateReqDTO.getProductId()).orElseThrow(NoSuchElementException::new);
            banner.update(bannerUpdateReqDTO, product);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?>  findAllBanner() {
        try{
            List<Banner> bannerList = bannerRepository.findAll();
            if(bannerList.size()<1){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bannerList.stream().map(BannerDTO.BannerResDTO::new).collect(Collectors.toList()), HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> findBanner(Long bannerId) {
        try{
            Banner banner =  bannerRepository.findById(bannerId).orElseThrow(NoSuchElementException::new);

            return new ResponseEntity<>(new BannerDTO.BannerResDTO(banner),HttpStatus.OK);
        }catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }



}
