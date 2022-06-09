package com.myproject.orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDetailRequestDto {
    private Long id;
    private int quantity;

    public OrderDetailRequestDto(){}

    @Builder
    public OrderDetailRequestDto(Long id, int quantity){
        this.id = id;
        this.quantity = quantity;
    }
}
