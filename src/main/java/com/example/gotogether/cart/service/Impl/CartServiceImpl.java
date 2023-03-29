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
import java.util.Optional;


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
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            Product product = productRepository.findById(addCartReqDTO.getProductId()).orElseThrow(NoSuchElementException::new);
            ProductOption productOption = productOptionRepository.findById(addCartReqDTO.getProductOptionId()).orElseThrow(NoSuchElementException::new);
            if (cartRepository.existsByUserAndProductAndProductOption(user, product, productOption)) {
                return new ResponseEntity<>("해당 상품과 같은 상품옵션은 이미 장바구니에 있습니다.", HttpStatus.BAD_REQUEST);
            }
            if (productOption.getProduct().getProductId() != addCartReqDTO.getProductId()){
                return new ResponseEntity<>("존재하지 않는 옵션입니다.", HttpStatus.BAD_REQUEST);
            }
            if(addCartReqDTO.getNumberOfPeople() >( productOption.getMaxPeople() - productOption.getPresentPeopleNumber())){
                return new ResponseEntity<>("예약 가능한 최대 인원수를 초과하였습니다.",HttpStatus.BAD_REQUEST);
            }
            if(addCartReqDTO.getSingleRoomNumber()>(productOption.getMaxSingleRoom()-productOption.getPresentPeopleNumber())){
                return new ResponseEntity<>("예약 가능한 최대 싱글를을 수를 초과하였습니다.",HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> deleteCart(UserDTO.UserAccessDTO userAccessDTO, Long cartId) {
        try {
            User user = userRepository.findByEmail(userAccessDTO.getEmail()).orElseThrow(NoSuchElementException::new);
            Optional<Cart> optionalCart = cartRepository.findById(cartId);
            if (optionalCart.isPresent()) {
                Cart cart = optionalCart.get();
                if (cart.getUser().equals(user)) {
                    cartRepository.delete(cart);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
                CartList.add(new CartDTO.CartListResDTO(
                        cart.getCartId(),
                        cart.getProduct().getProductId(),
                        cart.getProductOption().getProductOptionId(),
                        cart.getProduct().getName(),
                        cart.getProduct().getThumbnail(),
                        cart.getNumberOfPeople(),
                        cart.getSingleRoomNumber()));
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
            Cart cart = cartRepository.findById(cartId).orElseThrow(NoSuchElementException::new);
            ProductOption productOption = productOptionRepository.findById(updateCartReqDTO.getProductOptionId()).orElseThrow(NoSuchElementException::new);
            if (productOption.getProduct().getProductId() == cart.getProduct().getProductId() ||
                    productOption.getProductOptionId() == cart.getProductOption().getProductOptionId()) {
                return new ResponseEntity<>("해당 상품과 옵션이 이미 장바구니에 존재합니다.", HttpStatus.BAD_REQUEST);
            }
            if (productOption.getProduct().getProductId() != cart.getProduct().getProductId()){
                return new ResponseEntity<>("존재하지 않는 옵션입니다.", HttpStatus.BAD_REQUEST);
            }
            if(updateCartReqDTO.getNumberOfPeople()>(productOption.getMaxPeople()- productOption.getPresentPeopleNumber())){
                return new ResponseEntity<>("예약 가능한 최대 인원수를 초과하였습니다.",HttpStatus.BAD_REQUEST);
            }
            if(updateCartReqDTO.getSingleRoomNumber()>(productOption.getMaxSingleRoom()-productOption.getPresentPeopleNumber())){
                return new ResponseEntity<>("예약 가능한 최대 싱글를을 수를 초과하였습니다.",HttpStatus.BAD_REQUEST);
            }
            cart.update(updateCartReqDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }









}
