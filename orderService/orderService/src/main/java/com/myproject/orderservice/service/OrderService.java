package com.myproject.orderservice.service;

import com.myproject.orderservice.dto.*;
import com.myproject.orderservice.model.Food;
import com.myproject.orderservice.model.Restaurant;
import com.myproject.orderservice.repository.FoodRepository;
import com.myproject.orderservice.repository.OrderRepository;
import com.myproject.orderservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    OrderRepository orderRepository;
    RestaurantRepository restaurantRepository;
    FoodRepository foodRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository, FoodRepository foodRepository) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
    }

    // 고객이 입력한 선택한 데이터는 변하지 않는다고 가정 - DB에서 값이 업데이트되었을 경우를 대비해 수정 필요
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {

        Restaurant restaurant = restaurantRepository.findOne(requestDto.getRestaurantId());
        Food food;
        int totalCalPrice = 0;
        OrderServiceDto orderServiceDto;
        List<OrderDetailServiceDto> orderDetailServiceDtoList = new ArrayList<>();
        OrderDetailServiceDto orderDetailServiceDto;


        for(OrderDetailRequestDto dto : requestDto.getFoods()){
            food = foodRepository.findOne(dto.getId());

            // 주문 수량 검사
            if(dto.getQuantity() < 1) throw new IllegalArgumentException("주문 수량은 1 이상으로 입력해주세요.");
            if(dto.getQuantity() > 100) throw new IllegalArgumentException("주문 수량은 100 이하로 입력해주세요.");

            // 주문 금액 계산
            totalCalPrice = totalCalPrice + (food.getPrice()* dto.getQuantity());

            // 리포지터리에 넘겨줄 OrderDeetail 리스트 만들기
            orderDetailServiceDto = OrderDetailServiceDto.builder()
                    .price(food.getPrice())
                    .calPrice(food.getPrice()* dto.getQuantity())
                    .quantity(dto.getQuantity())
                    .food(food)
                    .build();
            orderDetailServiceDtoList.add(orderDetailServiceDto);
        }

        // 최소 주문 금액 검사
        if(totalCalPrice < restaurant.getMinOrderPrice()) throw new IllegalArgumentException
                ("최소 주문 금액 "+restaurant.getMinOrderPrice()+"원 이상 주문해야 합니다.");

        // 주문 금액 계산
        // ServiceDto로 만들어서 Repository에 넘겨줌
        orderServiceDto = OrderServiceDto.builder()
                .totalPrice(totalCalPrice + restaurant.getDeliveryFee())
                .totalCalPrice(totalCalPrice)
                .deliveryFee(restaurant.getDeliveryFee())
                .restaurant(restaurant)
                .build();

        return orderRepository.save(orderServiceDto, orderDetailServiceDtoList);
    }

    public List<OrderResponseDto> getOrders() {
        return orderRepository.findAll();
    }
}
