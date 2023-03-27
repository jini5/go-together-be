package com.example.gotogether.page.controller;

import com.example.gotogether.page.dto.RegionDTO;
import com.example.gotogether.page.service.PageContentsService;
import io.swagger.annotations.Api;
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

    @PostMapping("/regions")
    public ResponseEntity<?> addRegion(@RequestBody RegionDTO.RegionReqDTO dto){
        return pageContentsService.addRegion(dto);
    }

    @PutMapping("/regions/{regionId}")
    public ResponseEntity<?> updateRegion(@PathVariable Long regionId,@RequestBody RegionDTO.RegionUpdateReqDTO dto){
        return pageContentsService.updateRegion(regionId,dto);
    }

    @DeleteMapping("/regions/{regionId}")
    public ResponseEntity<?> deleteRegion(@PathVariable Long regionId){
        return pageContentsService.deleteRegion(regionId);
    }

}
