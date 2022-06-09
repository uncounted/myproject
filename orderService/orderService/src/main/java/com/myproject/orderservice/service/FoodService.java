package com.myproject.orderservice.service;

import com.myproject.orderservice.dto.FoodRequestDto;
import com.myproject.orderservice.model.Food;
import com.myproject.orderservice.repository.FoodRepository;
import com.myproject.orderservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public FoodService(FoodRepository foodRepository, RestaurantRepository restaurantRepository){
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public List<Food> getFoods(Long restaurantId){
        return foodRepository.findAll(restaurantId);
    }

    public List<Food> registerFood(Long restaurantId, List<FoodRequestDto> requestDto){
        List<String> foodnames = new ArrayList<>();
        for(FoodRequestDto dto:requestDto){
            //가격 검사
            if(dto.getPrice() < 100) throw new IllegalArgumentException("음식가격은 100원 이상 입력하세요.");
            if(dto.getPrice() > 1000000) throw new IllegalArgumentException("음식가격은 1,000,000원 미만으로 입력하세요.");
            if(dto.getPrice() % 100 != 0) throw new IllegalArgumentException("음식가격은 100원 단위로 입력하세요.");

            //음식명 중복 검사
            if(!foodnames.contains(dto.getName())) {
                foodnames.add(dto.getName());
            } else {
                throw new IllegalArgumentException
                        ("같은 레스토랑에는 중복된 음식명을 등록할 수 없습니다. 중복되지 않는 음식명을 작성해주세요.");
            }

            for(Food food:restaurantRepository.findOne(restaurantId).getMenu()){
                if(food.getName().equals(dto.getName())) throw new IllegalArgumentException
                        ("같은 레스토랑 내에 이미 중복된 음식명이 있습니다. 중복되지 않는 음식명을 작성해주세요.");
            }
        }
        return foodRepository.save(restaurantId, requestDto);
    }
}
