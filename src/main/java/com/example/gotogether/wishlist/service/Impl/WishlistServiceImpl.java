package com.example.gotogether.wishlist.service.Impl;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.repository.UserRepository;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.repository.ProductRepository;
import com.example.gotogether.wishlist.dto.WishlistDTO;
import com.example.gotogether.wishlist.entity.Wishlist;
import com.example.gotogether.wishlist.repository.WishlistRepository;
import com.example.gotogether.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Override
    @Transactional
    public ResponseEntity<?> createWishlist(UserDTO.UserAccessDTO userAccessDTO, WishlistDTO.WishReqDTO wishReqDTO) {
        try {
            Product product = productRepository.findById(wishReqDTO.getProductId()).orElseThrow(NoSuchElementException::new);
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            if (wishlistRepository.existsByUserAndProduct(user, product)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            wishlistRepository.save(new Wishlist(user, product));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteWishlist(UserDTO.UserAccessDTO userAccessDTO, WishlistDTO.DeleteWishReqDTO deletewishReqDTO) {
        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            if (wishlistRepository.existsByUserAndWishlistId(user, deletewishReqDTO.getWishlistId())) {
                wishlistRepository.deleteByUserAndWishlistId(user, deletewishReqDTO.getWishlistId());
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<?>> findAllWishlistDTO(UserDTO.UserAccessDTO userAccessDTO) {
        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            List<Wishlist> wishlists = wishlistRepository.findAllByUser(user);
            if (wishlists.size()<1){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<WishlistDTO.WishlistResDTO> allWishlist = wishlists.stream().map(WishlistDTO.WishlistResDTO::new).collect(Collectors.toList());
            return new ResponseEntity<>(allWishlist, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
