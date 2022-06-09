package com.myproject.orderservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Order extends Timestamper{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ORDER_ID")
    private Long id;

    /**
     * 지불금액
     * = 총 상품가격 + 배달비
     */
    private int totalPrice;

    /**
     * 총 상품가격(배달비 제외)
     * = OrderDetails.calPrice의 총합
     * 총 상품가격은 항상 음식점.최소주문가격 이상이다.
     * 고객은 음식점 한 곳에서 여러 종류의 음식을 주문할 수 있다.
     */
    private int price;

    /**
     * 배달비
     * 데이터 기록용으로, fk 관계를 맺지 않고 단독 저장한다.
     * 현재는 음식 주문은 음식점 한 곳에서만 가능한 것으로 제약하며,
     * 배달비는 별도 로직없이 음식점의 기본 배달비를 저장한다.
     */
    private int deliveryFee;

    @ManyToOne
    @JoinColumn(name="RESTAURANT_ID")
    @JsonBackReference(value="order-restaurant-fk")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order")
    @JsonManagedReference(value="orderDetail-order-fk")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    //private String userId;

    public Order(){}

    @Builder
    public Order(int totalPrice, int price, int deliveryFee, Restaurant restaurant){
        this.totalPrice = totalPrice;
        this.price = price;
        this.deliveryFee = deliveryFee;
        this.restaurant = restaurant;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
