package com.myproject.orderservice.dto;

import com.myproject.orderservice.model.Food;
import com.myproject.orderservice.model.Restaurant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodRequestDto{
    private String name;
    private int price;
    private Restaurant restaurant;

    public FoodRequestDto(String name, int price, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.restaurant  = restaurant;
    }

}
