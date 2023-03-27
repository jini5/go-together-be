package com.example.gotogether.product.entity;

import com.example.gotogether.product.dto.ProductOptionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "product_option")
public class ProductOption {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @Column(name = "product_option_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ProductOptionId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "max_people")
    private int maxPeople;

    @Column(name = "max_single_room")
    private int maxSingleRoom;

    @Column(name = "present_people_number")
    private int PresentPeopleNumber;

    @Column(name = "present_single_room_number")
    private int PresentSingleRoomNumber;


    @Builder
    public ProductOption(Product product, LocalDate startDate, LocalDate endDate, int maxPeople, int maxSingleRoom, int presentPeopleNumber, int presentSingleRoomNumber) {
        this.product = product;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxPeople = maxPeople;
        this.maxSingleRoom = maxSingleRoom;
        this.PresentPeopleNumber = presentPeopleNumber;
        this.PresentSingleRoomNumber = presentSingleRoomNumber;
    }

//    public void update(ProductOptionDTO.ProductOptionReqDTO productOptionReqDTO){
//        this.startDate=productOptionReqDTO.getStartDate();
//        this.endDate=productOptionReqDTO.getEndDate();
//        this.maxPeople=productOptionReqDTO.getMaxPeople();
//        this.maxSingleRoom=productOptionReqDTO.getMaxSingleRoom();
//    }
}
