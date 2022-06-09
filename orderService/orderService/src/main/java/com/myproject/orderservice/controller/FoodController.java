package com.myproject.orderservice.controller;

import com.myproject.orderservice.dto.FoodRequestDto;
import com.myproject.orderservice.model.Food;
import com.myproject.orderservice.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService){
        this.foodService = foodService;
    }

    @GetMapping("/restaurant/{restaurantId}/foods")
    public @ResponseBody List<Food> getFoods(@PathVariable Long restaurantId){
        return foodService.getFoods(restaurantId);
    }

    @PostMapping("/restaurant/{restaurantId}/food/register")
    public @ResponseBody List<Food> registerFood(@PathVariable Long restaurantId, @RequestBody List<FoodRequestDto> requestDto){
        foodService.registerFood(restaurantId, requestDto);
        return null;
    }
}
