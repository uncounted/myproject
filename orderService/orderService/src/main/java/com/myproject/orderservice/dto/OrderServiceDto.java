package com.myproject.orderservice.dto;

import com.myproject.orderservice.model.Food;
import com.myproject.orderservice.model.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderServiceDto {

    //Order 정보
    private int totalPrice;
    private int totalCalPrice;
    private int deliveryFee;
    private Restaurant restaurant;
}
