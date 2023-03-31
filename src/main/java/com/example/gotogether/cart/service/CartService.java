package com.example.gotogether.cart.service;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.cart.dto.CartDTO;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

public interface CartService {
    @Transactional
    ResponseEntity<?> addCart(UserDTO.UserAccessDTO userAccessDTO, CartDTO.AddCartReqDTO addCartReqDTO);

    @Transactional
    ResponseEntity<?> deleteCart(UserDTO.UserAccessDTO userAccessDTO, Long cartId);

    @Transactional
    ResponseEntity<?> getCartList(UserDTO.UserAccessDTO userAccessDTO);

    ResponseEntity<?> updateCart(Long cartId, CartDTO.UpdateCartReqDTO updateCartReqDTO);
}
