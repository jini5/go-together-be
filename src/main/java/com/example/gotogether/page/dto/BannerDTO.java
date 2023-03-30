package com.example.gotogether.page.dto;

import com.example.gotogether.page.entity.Banner;
import com.example.gotogether.product.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

public class BannerDTO {

    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "배너 추가")
    @ToString
    public static class BannerReqDTO{
        @ApiModelProperty(value = "배너 이미지", required = true)
        private String image;
        @ApiModelProperty(value = "상품", required = true)
        private Long productId;


        @Builder
        public BannerReqDTO(String image, Long productId) {
            this.image = image;
            this.productId = productId;
        }
        public Banner toEntity(){
            Banner banner = Banner.builder()
                    .image(image)
                    .build();
            return banner;
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "배너 수정")
    @ToString
    public static class BannerUpdateReqDTO{
        @ApiModelProperty(value = "배너 이미지", required = true)
        private String image;
        @ApiModelProperty(value = "상품", required = true)
        private Long productId;

    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "배너 보이기")
    @ToString
    public static class BannerResDTO{
        @ApiModelProperty(value = "배너 아이디", required = true)
        private Long bannerId;
        @ApiModelProperty(value = "배너 이미지", required = true)
        private String image;
        @ApiModelProperty(value = "상품", required = true)
        private Long productId;

        public BannerResDTO(Banner banner) {
            this.image=banner.getImage();
            this.productId = banner.getProduct().getProductId();
        }

    }


}
