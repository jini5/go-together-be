package com.example.gotogether.page.entity;

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

    @Column(name = "region_image")
    private String region;

    @Column(name = "region_rate")
    private int rate;

}
