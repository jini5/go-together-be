package com.example.gotogether.wishlist.controller;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.wishlist.dto.WishlistDTO;
import com.example.gotogether.wishlist.service.WishlistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"위시리스트 서비스"}, description = "위시리스트 추가, 위시리스트삭제")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/wishlist")
    @ApiOperation(value = "위시리스트 상품 추가", notes = "상품ID를 통해 위시리스트 목록에 상품을 추가한다. \n\n" + "code: 200 위시리스트 추가 성공,  code: 400 해당 상품이 이미 있음, code: 401 로그인X , code: 500 서버에러")
    public ResponseEntity<?> addWishlist(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO, @RequestBody WishlistDTO.WishReqDTO wishReqDTO) {
        return wishlistService.createWishlist(userAccessDTO, wishReqDTO);
    }

    @DeleteMapping("/wishlist")
    @ApiOperation(value = "위시리스트 상품 삭제", notes = "상품ID를 통해 위시리스트 목록에 상품을 추가한다. \n\n" + "code: 200 위시리스트 삭제 성공, code: 400 이미 위시리스트에서 삭제됨, code: 500 서버에러")
    public ResponseEntity<?> deleteWishlist(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO, @RequestBody WishlistDTO.DeleteWishReqDTO deletewishReqDTO) {
        return wishlistService.deleteWishlist(userAccessDTO, deletewishReqDTO);
    }

    @GetMapping("/wishlist")
    @ApiOperation(value = "내 위시리스트 목록", notes = "회원의 위시리스트 목록을 확인한다. \n\n" + "code: 200 위시리스트 삭제 성공, code: 204 위시리스트가 없음, code: 500 서버에러")
    public ResponseEntity<?> findAllWishlist(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO) {
        return wishlistService.findAllWishlistDTO(userAccessDTO);
    }

}
