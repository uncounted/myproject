package com.myproject.orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderRequestDto {
    Long restaurantId;
    List<OrderDetailRequestDto> foods;

    public OrderRequestDto(){}

    @Builder
    public OrderRequestDto(Long restaurantId, List<OrderDetailRequestDto> foods){
        this.restaurantId = restaurantId;
        this.foods = foods;
    }
}
