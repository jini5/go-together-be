package com.example.gotogether.page.dto;

import com.example.gotogether.page.entity.Region;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class RegionDTO {

    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "상품 추가")
    @ToString
    public static class RegionReqDTO{
        @ApiModelProperty(value = "지역 이미지", required = true)
        private String image;
        @ApiModelProperty(value = "지역 이름", required = true)
        private String regionName;
        @ApiModelProperty(value = "지역 노출 순위", required = true)
        private int rate;

        public Region toEntity(){
            return Region.builder()
                    .regionName(regionName)
                    .image(image)
                    .rate(rate)
                    .build();
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "인기 지역 보이기")
    @ToString
    public static class RegionResDTO{
        @ApiModelProperty(value = "지역 아이디", required = true)
        private Long RegionId;
        @ApiModelProperty(value = "지역 이미지", required = true)
        private String image;
        @ApiModelProperty(value = "지역 이름", required = true)
        private String regionName;
        @ApiModelProperty(value = "지역 노출 순위", required = true)
        private int rate;

        public RegionResDTO(Region region){
            this.RegionId = region.getRegionId();
            this.image = region.getImage();
            this.regionName = region.getRegionName();
            this.rate = region.getRate();
        }
    }
    @NoArgsConstructor
    @Getter
    @Setter
    @ApiModel(value = "인기 지역 수정")
    @ToString
    public static class RegionUpdateReqDTO{
        @ApiModelProperty(value = "지역 이미지", required = true)
        private String image;
        @ApiModelProperty(value = "지역 이름", required = true)
        private String regionName;
        @ApiModelProperty(value = "지역 노출 순위", required = true)
        private int rate;

    }
}
