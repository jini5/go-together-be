package com.example.gotogether.page.dto;

import com.example.gotogether.page.entity.Banner;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class BannerDTO {

    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "배너 추가")
    @ToString
    public static class BannerReqDTO {
        @ApiModelProperty(value = "배너 이미지", required = true)
        private String image;
        @ApiModelProperty(value = "상품", required = true)
        private Long productId;
        @ApiModelProperty(value = "배너 테그", required = true)
        private String tag;
        @ApiModelProperty(value = "배너 제목", required = true)
        private String title;
        @ApiModelProperty(value = "배너 부제목", required = true)
        private String subtitle;

        public Banner toEntity() {
            return Banner.builder()
                    .image(image)
                    .tag(tag)
                    .title(title)
                    .subtitle(subtitle)
                    .build();
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "배너 수정")
    @ToString
    public static class BannerUpdateReqDTO {
        @ApiModelProperty(value = "배너 이미지", required = true)
        private String image;
        @ApiModelProperty(value = "상품", required = true)
        private Long productId;
        @ApiModelProperty(value = "배너 테그", required = true)
        private String tag;
        @ApiModelProperty(value = "배너 제목", required = true)
        private String title;
        @ApiModelProperty(value = "배너 부제목", required = true)
        private String subtitle;

    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "배너 보이기")
    @ToString
    public static class BannerResDTO {
        @ApiModelProperty(value = "배너 아이디", required = true)
        private Long bannerId;
        @ApiModelProperty(value = "배너 이미지", required = true)
        private String image;
        @ApiModelProperty(value = "상품", required = true)
        private Long productId;
        @ApiModelProperty(value = "배너 테그", required = true)
        private String tag;
        @ApiModelProperty(value = "배너 제목", required = true)
        private String title;
        @ApiModelProperty(value = "배너 부제목", required = true)
        private String subtitle;

        public BannerResDTO(Banner banner) {
            this.bannerId = banner.getBannerId();
            this.image = banner.getImage();
            this.productId = banner.getProduct().getProductId();
            this.tag = banner.getTag();
            this.title = banner.getTitle();
            this.subtitle = banner.getSubtitle();
        }

    }


}
