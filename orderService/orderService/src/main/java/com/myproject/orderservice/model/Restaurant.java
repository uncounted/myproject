package com.myproject.orderservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myproject.orderservice.dto.RestaurantDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Restaurant extends Timestamper{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="RESTAURANT_ID")
    private Long id;
    private String name;

    /**
     * 최소주문금액
     * 허용값: 1,000원 ~ 100,000원
     * 단위: 100원
     */
    private int minOrderPrice;

    /**
     * 기본배달비
     * 허용값: 0원 ~ 10,000원
     * 단위: 500원
     */
    private int deliveryFee;

    /**
     * Food에서 생성된 restaurant 객체와 매핑
     * (주인이 아닌 menu는 읽기 전용)
     */
    @OneToMany (mappedBy = "restaurant")
    @JsonManagedReference(value="food-restaurant-fk")
    private final List<Food> menu = new ArrayList<Food>();

    @OneToMany (mappedBy = "restaurant")
    @JsonManagedReference(value="order-restaurant-fk")
    private List<Order> orders = new ArrayList<>();

    public Restaurant(){}

    public Restaurant(RestaurantDto requestDto){
        this.name = requestDto.getName();
        this.minOrderPrice = requestDto.getMinOrderPrice();
        this.deliveryFee = requestDto.getDeliveryFee();
    }
}
