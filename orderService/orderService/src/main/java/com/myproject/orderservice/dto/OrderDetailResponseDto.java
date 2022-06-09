package com.myproject.orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailResponseDto {
    private String restaurantName;
    private String name;
    private int deliveryFee;
    private int price;

    public OrderDetailResponseDto(){}

    @Builder
    public OrderDetailResponseDto(String restaurantName, String foodName, int deliveryFee, int price) {
        this.restaurantName = restaurantName;
        this.name = foodName;
        this.deliveryFee = deliveryFee;
        this.price = price;
    }
}
