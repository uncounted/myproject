package com.myproject.orderservice.dto;

import com.myproject.orderservice.model.Food;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OrderDetailServiceDto {
    //OrderDetail정보
    private int price;
    private int calPrice;
    private int quantity;
    private Food food;
}
