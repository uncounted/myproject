package com.myproject.orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private String restaurantName;
    private List<OrderDetailResponseDto> foods;
    private int deliveryFee;
    private int totalPrice;

    @Builder
    public OrderResponseDto(String restaurantName, List<OrderDetailResponseDto> foods, int deliveryFee, int totalPrice) {
        this.restaurantName = restaurantName;
        this.foods = foods;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
    }
}
