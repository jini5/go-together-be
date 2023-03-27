package com.example.gotogether.page.controller;

import com.example.gotogether.page.service.PageContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PageContentsController {

    private final PageContentsService pageContentsService;

    @GetMapping("/page/region")
    public ResponseEntity<?> getRegionList(){
        return pageContentsService.getRegionList();
    }
}
