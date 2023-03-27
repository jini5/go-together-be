package com.example.gotogether.page.controller;

import com.example.gotogether.page.dto.RegionDTO;
import com.example.gotogether.page.service.PageContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class PageContentsAdminController {

    private final PageContentsService pageContentsService;

    @PostMapping("/region")
    public ResponseEntity<?> addRegion(@RequestBody RegionDTO.RegionReqDTO dto){
        return pageContentsService.addRegion(dto);
    }

    @PutMapping("/region/{regionId}")
    public ResponseEntity<?> updateRegion(@PathVariable Long regionId,@RequestBody RegionDTO.RegionUpdateReqDTO dto){
        return pageContentsService.updateRegion(regionId,dto);
    }

}
