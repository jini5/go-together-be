package com.example.gotogether.wishlist.service;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.wishlist.dto.WishlistDTO;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;


public interface WishlistService {


    @Transactional
    ResponseEntity<?> createWishlist(UserDTO.UserAccessDTO userAccessDTO, WishlistDTO.WishReqDTO wishReqDTO);

    @Transactional
    ResponseEntity<?> deleteWishlist(UserDTO.UserAccessDTO userAccessDTO, Long wishlistId);

    @Transactional
    ResponseEntity<?> findAllWishlistDTO(UserDTO.UserAccessDTO userAccessDTO);
}
