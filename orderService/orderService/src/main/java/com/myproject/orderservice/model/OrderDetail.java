package com.myproject.orderservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderDetail extends Timestamper{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 음식 개당 가격
     * 음식 가격은 변동될 수 있기 때문에 주문 내역에 별도 저장
    */
    private int price;

    /**
     * food.price * 주문 수량
     */
    private int calPrice;

    /**
     * 음식 한 종류 당 주문 수량
     * 허용값: 1 ~ 100
     */
    private int quantity;

    @ManyToOne
    @JoinColumn(name="FOOD_ID")
    @JsonBackReference(value="orderDetail-food-fk")
    private Food food;

    @ManyToOne
    @JoinColumn(name="ORDER_ID")
    @JsonBackReference(value="orderDetail-order-fk")
    private Order order;

    public OrderDetail() {
    }

    @Builder
    public OrderDetail(int price, int calPrice, int quantity, Food food, Order order) {
        this.price = price;
        this.calPrice = calPrice;
        this.quantity = quantity;
        this.food = food;
        this.order = order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
