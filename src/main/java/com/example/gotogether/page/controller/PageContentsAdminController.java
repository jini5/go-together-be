package com.example.gotogether.page.controller;

import com.example.gotogether.page.dto.BannerDTO;
import com.example.gotogether.page.dto.RegionDTO;
import com.example.gotogether.page.service.BannerService;
import com.example.gotogether.page.service.PageContentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
@Api(tags = {"관리자의 페이지 컨텐츠 관리 서비스"}, description = "인기 지역 추가,수정,삭제, & 배너 추가,수정,삭제")
public class PageContentsAdminController {

    private final PageContentsService pageContentsService;
    private final BannerService bannerService;

    @PostMapping("/regions")
    @ApiOperation(value = "인기 지역 추가", notes = "관리자가 인기 여행지역 추가. rate 는 노출 단계(낮을수록 먼저 노출) \n\n" +
            "code: 201 추가 성공, 400 지역 이름 중복")
    public ResponseEntity<?> addRegion(@RequestBody RegionDTO.RegionReqDTO dto) {
        return pageContentsService.addRegion(dto);
    }

    @PutMapping("/regions/{regionId}")
    @ApiOperation(value = "인기 지역 수정", notes = "관리자가 인기 여행지역 수정.\n\n" +
            "code: 200 수정 성공, 400 지역 이름 중복, 404 잘못된 ID")
    public ResponseEntity<?> updateRegion(@PathVariable Long regionId, @RequestBody RegionDTO.RegionUpdateReqDTO dto) {
        return pageContentsService.updateRegion(regionId, dto);
    }

    @DeleteMapping("/regions/{regionId}")
    @ApiOperation(value = "인기 지역 삭제", notes = "관리자가 인기 여행지역 삭제.\n\n" +
            "code: 200 삭제 성공, 404 잘못된 ID")
    public ResponseEntity<?> deleteRegion(@PathVariable Long regionId) {
        return pageContentsService.deleteRegion(regionId);
    }

    @GetMapping("/regions/{regionId}")
    @ApiOperation(value = "인기 지역 상세 조회", notes = "관리자가 인기 여행지역 상세 조회.\n\n" +
            "code: 200 조회 성공, 404 잘못된 ID")
    public ResponseEntity<?> getRegionDetail(@PathVariable Long regionId) {
        return pageContentsService.getRegionDetail(regionId);
    }

    //배너

    @PostMapping("/page/banner")
    @ApiOperation(value = "배너 추가", notes = "관리자가 배너 추가.  \n\n" +
            "code: 201 추가 성공, 400 추가 불가능")
    public ResponseEntity<?> addBanner(@RequestBody BannerDTO.BannerReqDTO bannerReqDTO) {
        return bannerService.addBanner(bannerReqDTO);
    }

    @PutMapping("/page/banner/{bannerId}")
    @ApiOperation(value = "배너 수정", notes = "관리자가 배너 수정.\n\n" +
            "code: 200 수정 성공, 400 배너 중복, 400 잘못된 요청, 404 잘못된 ID")
    public ResponseEntity<?> updateBanner(@PathVariable Long bannerId, @RequestBody BannerDTO.BannerUpdateReqDTO bannerUpdateReqDTO) {
        return bannerService.updateBanner(bannerId, bannerUpdateReqDTO);
    }

    @DeleteMapping("/page/banner/{bannerId}")
    @ApiOperation(value = "배너 삭제", notes = "관리자가 배너 삭제.\n\n" +
            "code: 200 삭제 성공, 404 잘못된 ID, 400 잘못된 요청")
    public ResponseEntity<?> deleteBanner(@PathVariable Long bannerId) {
        return bannerService.deleteBanner(bannerId);
    }


    @GetMapping("/page/bannerlist")
    @ApiOperation(value = "배너 리스트", notes = "배너 리스트 제공. \n\n" +
            "code: 200 조회 성공, 204 표시할 배너 없음, 400 잘못된 요청")
    public ResponseEntity<?> getBannerList() {
        return bannerService.findAllBanner();
    }

    @GetMapping("/page/banner/{bannerId}")
    @ApiOperation(value = "배너 조회", notes = "개별 배너 제공. \n\n" +
            "code: 200 조회 성공, 204 표시할 배너 없음, 400 잘못된 요청")
    public ResponseEntity<?> getBanner(@PathVariable Long bannerId) {
        return bannerService.findBanner(bannerId);
    }

}
