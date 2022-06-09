package com.myproject.orderservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.myproject.orderservice.dto.FoodRequestDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Food extends Timestamper {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="FOOD_ID")
    private Long id;

    /**
     * 음식명
     * 같은 음식점 내에서는 이름 중복 불가
     */
    private String name;

    /**
     * 음식 가격(수량 1 당)
     * 허용값: 100원 ~ 1,000,000원
     * 단위: 100원
     */
    private int price;

    /**
     * Restaurant의 Id를 FK로 사용
     * 연관관계의 주인
     */
    @ManyToOne
    @JoinColumn(name="RESTAURANT_ID")
    @JsonBackReference(value="food-restaurant-fk")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "food")
    @JsonManagedReference(value="orderDetail-food-fk")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Food() {
    }

    public Food(FoodRequestDto requestDto) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.restaurant = requestDto.getRestaurant();
    }



//    public void changeRestaurant(Restaurant restaurant){
//        if(this.restaurant != null){
//            this.restaurant.getMenu().remove(this);
//        }
//        this.restaurant = restaurant;
//        restaurant.getMenu().add(this);
//    }

}
