package com.example.gotogether.page.entity;

import com.example.gotogether.page.dto.RegionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "region_image")
    private String image;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "region_rate")
    private int rate;

    @Builder
    public Region(String image, String regionName, int rate) {
        this.image = image;
        this.regionName = regionName;
        this.rate = rate;
    }

    public void update(RegionDTO.RegionUpdateReqDTO dto) {
        this.image = dto.getImage();
        this.regionName = dto.getRegionName();
        this.rate = dto.getRate();
    }
}
