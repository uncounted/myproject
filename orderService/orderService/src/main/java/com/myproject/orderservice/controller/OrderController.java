package com.myproject.orderservice.controller;

import com.myproject.orderservice.dto.OrderResponseDto;
import com.myproject.orderservice.dto.OrderRequestDto;
import com.myproject.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public @ResponseBody List<OrderResponseDto> getOrders(){
        return orderService.getOrders();
    }

    @PostMapping("/order/request")
    public @ResponseBody OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto){
        System.out.println("order controller");
        return orderService.createOrder(requestDto);
    }

}
