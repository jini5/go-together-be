package com.example.gotogether.cart.service.Impl;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.auth.entity.User;
import com.example.gotogether.auth.repository.UserRepository;
import com.example.gotogether.cart.dto.CartDTO;
import com.example.gotogether.cart.entity.Cart;
import com.example.gotogether.cart.repository.CartRepository;
import com.example.gotogether.cart.service.CartService;
import com.example.gotogether.product.entity.Product;
import com.example.gotogether.product.entity.ProductOption;
import com.example.gotogether.product.repository.ProductOptionRepository;
import com.example.gotogether.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;

    @Override
    @Transactional
    public ResponseEntity<?> addCart(UserDTO.UserAccessDTO userAccessDTO, CartDTO.AddCartReqDTO addCartReqDTO) {
        try {
            if(addCartReqDTO.getNumberOfPeople()<1){
                return new ResponseEntity<>("NumberOfPeople must be bigger then 0",HttpStatus.BAD_REQUEST);
            }
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            Product product = productRepository.findById(addCartReqDTO.getProductId()).orElseThrow(NoSuchElementException::new);
            ProductOption productOption = productOptionRepository.findById(addCartReqDTO.getProductOptionId()).orElseThrow(NoSuchElementException::new);
            if (cartRepository.existsByUserAndProductAndProductOption(user, product, productOption)) {
                return new ResponseEntity<>("The product and product options for that product are already in your shopping cart.", HttpStatus.BAD_REQUEST);
            }
            if (productOption.getProduct().getProductId() != addCartReqDTO.getProductId()) {
                return new ResponseEntity<>("This option does not exist.", HttpStatus.BAD_REQUEST);
            }
            if (addCartReqDTO.getNumberOfPeople() > (productOption.getMaxPeople() - productOption.getPresentPeopleNumber())) {
                return new ResponseEntity<>("The maximum number of people that can be reserved has been exceeded.", HttpStatus.BAD_REQUEST);
            }
            if (addCartReqDTO.getSingleRoomNumber() > (productOption.getMaxSingleRoom() - productOption.getPresentSingleRoomNumber())) {
                return new ResponseEntity<>("You have exceeded the maximum number of singles that can be booked.", HttpStatus.BAD_REQUEST);
            }
            Cart cart = Cart.builder()
                    .user(user)
                    .product(product)
                    .productOption(productOption)
                    .numberOfPeople(addCartReqDTO.getNumberOfPeople())
                    .singleRoomNumber(addCartReqDTO.getSingleRoomNumber())
                    .build();

            cartRepository.save(cart);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteCart(UserDTO.UserAccessDTO userAccessDTO, List<Long> cartIdList) {
        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            List<Cart> cartList = cartRepository.findAllById(cartIdList);
            if (cartList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            for (Cart cart : cartList) {
                if (cart.getUser().equals(user)) {
                    cartRepository.delete(cart);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> getCartList(UserDTO.UserAccessDTO userAccessDTO) {
        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            List<Cart> cartList = cartRepository.findAllByUser(user);
            List<CartDTO.CartListResDTO> CartList = new ArrayList<>();

            for (Cart cart : cartList) {
                CartList.add(new CartDTO.CartListResDTO(cart));
            }
            if (cartList.size() < 1) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(CartList, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateCart(Long cartId, CartDTO.UpdateCartReqDTO updateCartReqDTO) {
        try {
            //카트 찾고
            Cart cart = cartRepository.findById(cartId).orElseThrow(NoSuchElementException::new);
            //new 옵션
            ProductOption productOption = productOptionRepository.findById(updateCartReqDTO.getProductOptionId()).orElseThrow(NoSuchElementException::new);
            if (productOption.getProduct().getProductId() != cart.getProduct().getProductId()) {
                return new ResponseEntity<>("This option does not exist.", HttpStatus.BAD_REQUEST);
            }
            // 인원 및 싱글룸 유효성 검사
            if (updateCartReqDTO.getNumberOfPeople() > (productOption.getMaxPeople() - productOption.getPresentPeopleNumber())) {
                return new ResponseEntity<>("The maximum number of people that can be reserved has been exceeded.", HttpStatus.BAD_REQUEST);
            }
            if (updateCartReqDTO.getSingleRoomNumber() > (productOption.getMaxSingleRoom() - productOption.getPresentSingleRoomNumber())) {
                return new ResponseEntity<>("You have exceeded the maximum number of singles that can be booked.", HttpStatus.BAD_REQUEST);
            }
            cart.update(updateCartReqDTO, productOption);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
