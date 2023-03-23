package com.example.gotogether.global.aws;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"AWS"}, description = "AWS S3 관련 서비스")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;

    @PostMapping("/image")
    public ResponseEntity<?> uploadFile(
            @RequestParam("category") String category,
            @RequestPart(value = "file") MultipartFile multipartFile) {
        return awsS3Service.uploadFileV1(category, multipartFile);
    }

}
