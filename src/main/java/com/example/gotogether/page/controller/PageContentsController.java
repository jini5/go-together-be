package com.example.gotogether.page.controller;

import com.example.gotogether.auth.dto.UserDTO;
import com.example.gotogether.page.service.BannerService;
import com.example.gotogether.page.service.PageContentsService;
import com.example.gotogether.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Api(tags = {"페이지 컨텐츠 서비스"}, description = "인기 지역 리스트, 배너 리스트, 인기 상품 ")
public class PageContentsController {

    private final PageContentsService pageContentsService;
    private final ProductService productService;

    private final BannerService bannerService;

    @GetMapping("/page/popular/regions")
    @ApiOperation(value = "인기 지역 리스트", notes = "관리자가 제작한 인기 여행지역 리스트 제공. \n\n" +
            "code: 200 조회 성공, 204 표시할 지역 없음")
    public ResponseEntity<?> getRegionList() {
        return pageContentsService.getRegionList();
    }

    @GetMapping("/page/popular/products")
    @ApiOperation(value = "인기 상품 검색(전체 or 특정 카테고리 가능)", notes = "categoryID 를 받을경우 그 카테고리의 인기순 상품 10개 제공.없을시 전체 상품 중 인기순 10개 제공. \n\n" +
            "code: 200 상품 목록 조회 성공, 204 표시할 상품 없음, 400 잘못된 카테고리 요청, 500 서버에러 ")
    public ResponseEntity<?> findPopularProducts(@RequestParam(required = false) Long categoryId) {
        return productService.findPopularProducts(categoryId);
    }

    @GetMapping("/page/group/products")
    @ApiOperation(value = "사용자 그룹의 상품 추천", notes = "사용자가 속한 그룹의 추천 상품 제공. \n\n" +
            "code: 200 그룹 상품 목록 조회 성공, 204 표시할 상품 없음, 400 사용자의 타입 없음, 500 서버에러, 404 없는 사용자 ")
    public ResponseEntity<?> findGroupProduct(@AuthenticationPrincipal UserDTO.UserAccessDTO userAccessDTO) {
        return pageContentsService.findGroupProduct(userAccessDTO);
    }

    //배너
    @GetMapping("/page/banners")
    @ApiOperation(value = "배너 리스트", notes = "배너 리스트 제공. \n\n" +
            "code: 200 조회 성공, 204 표시할 배너 없음, 400 잘못된 요청")
    public ResponseEntity<?> BannerList() {
        return bannerService.findAllBanner();
    }

}
