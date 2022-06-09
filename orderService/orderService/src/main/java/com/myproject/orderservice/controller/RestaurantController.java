package com.myproject.orderservice.controller;

import com.myproject.orderservice.dto.RestaurantDto;
import com.myproject.orderservice.model.Restaurant;
import com.myproject.orderservice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService){
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public @ResponseBody Restaurant[] getRestaurants(){
        return restaurantService.getRestaurants();
    }

    @PostMapping("/restaurant/register")
    public @ResponseBody Restaurant registerRestaurant(@RequestBody RestaurantDto requestDto){
        return restaurantService.registerRestaurant(requestDto);
    }
}
