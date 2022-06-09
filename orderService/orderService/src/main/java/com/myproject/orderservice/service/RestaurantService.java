package com.myproject.orderservice.service;

import com.myproject.orderservice.dto.RestaurantDto;
import com.myproject.orderservice.model.Restaurant;
import com.myproject.orderservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant[] getRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant registerRestaurant(RestaurantDto requestDto){
        //기본 배달비 유효성 검사
        if (requestDto.getDeliveryFee() < 0) throw new IllegalArgumentException("기본 배달비는 0원 이상으로 입력해주세요.");
        if (requestDto.getDeliveryFee() > 10000) throw new IllegalArgumentException("기본 배달비는 10,000원 이하로 입력해주세요.");
        if (requestDto.getDeliveryFee() % 500 != 0) throw new IllegalArgumentException("기본 배달비는 500원 단위로 입력해주세요.");

        //최소 주문 가격 검사
        if(requestDto.getMinOrderPrice() < 1000) throw new IllegalArgumentException("최소 주문 가격은 1000원 이상으로 입력해주세요.");
        if(requestDto.getMinOrderPrice() > 100000) throw new IllegalArgumentException("최소 주문 가격은 100,000원 이하로 입력해주세요.");
        if(requestDto.getMinOrderPrice() % 100 != 0) throw new IllegalArgumentException("최소 주문 가격은 100원 단위로 입력해주세요.");

        //음식점 중복 검사
//        if(restaurantRepository.findOneByName(requestDto.getName()).size() > 1) throw new IllegalArgumentException
//                ("같은 이름을 가진 레스토랑이 있습니다. 중복되지 않은 이름을 등록해주세요.");

        return restaurantRepository.save(requestDto);
    }
}
