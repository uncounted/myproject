package com.myproject.orderservice.repository;

import com.myproject.orderservice.dto.*;
import com.myproject.orderservice.model.Order;
import com.myproject.orderservice.model.OrderDetail;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class OrderRepository {
    @PersistenceContext
    private EntityManager em;

    public List<OrderResponseDto> findAll() {
        List<Order> orders = em.createQuery("select o from Order o", Order.class)
                .getResultList();
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for(int i=0; i<orders.size(); i++){
            String restaurantName = orders.get(i).getRestaurant().getName();
            int deliveryFee = orders.get(i).getDeliveryFee();
            int totalPrice = orders.get(i).getTotalPrice();
            OrderDetailResponseDto orderDetailResponseDto = new OrderDetailResponseDto();
            List<OrderDetailResponseDto> orderDetailResponseDtoList = new ArrayList<>();

            for(int j = 0; j<orders.get(i).getOrderDetails().size(); j++){
                orderDetailResponseDto = OrderDetailResponseDto.builder()
                        .restaurantName(restaurantName)
                        .foodName(orders.get(i).getOrderDetails().get(j).getFood().getName())
                        .deliveryFee(deliveryFee)
                        .price(orders.get(i).getOrderDetails().get(j).getCalPrice())
                        .build();
                orderDetailResponseDtoList.add(orderDetailResponseDto);
            }

            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .restaurantName(restaurantName)
                    .foods(orderDetailResponseDtoList)
                    .deliveryFee(deliveryFee)
                    .totalPrice(totalPrice)
                    .build();
            orderResponseDtoList.add(orderResponseDto);
        }
        System.out.println(orderResponseDtoList.size());

        return orderResponseDtoList;
    }

    public OrderResponseDto findById(Long orderId){
        Order orderGiven = em.find(Order.class, orderId);
        List<OrderDetail> orderDetailsGiven = em.createQuery("select o from OrderDetail o where o.order.id=:id", OrderDetail.class)
                .setParameter("id", orderId)
                .getResultList();

        List<OrderDetailResponseDto> response = new ArrayList<>();
        for (OrderDetail orderDetailGiven : orderDetailsGiven) {
            OrderDetailResponseDto orderDetailResponseDto = OrderDetailResponseDto.builder()
                    .restaurantName(orderGiven.getRestaurant().getName())
                    .foodName(orderDetailGiven.getFood().getName())
                    .price(orderDetailGiven.getCalPrice())
                    .deliveryFee(orderGiven.getDeliveryFee())
                    .build();
            response.add(orderDetailResponseDto);
        }

        return OrderResponseDto.builder()
                .restaurantName(orderGiven.getRestaurant().getName())
                .foods(response)
                .deliveryFee(orderGiven.getDeliveryFee())
                .totalPrice(orderGiven.getTotalPrice())
                .build();
    }

    public OrderResponseDto save(OrderServiceDto serviceDto, List<OrderDetailServiceDto> detailServiceDtoList){
        System.out.println("save");
        System.out.println(serviceDto.getTotalPrice());
        Order order = Order.builder()
                .totalPrice(serviceDto.getTotalPrice())
                .price(serviceDto.getTotalCalPrice())
                .deliveryFee(serviceDto.getDeliveryFee())
                .restaurant(serviceDto.getRestaurant())
                .build();

        em.persist(order);
        saveOrderDetail(order, detailServiceDtoList);

        return findById(order.getId());
    }

    public List<OrderDetail> saveOrderDetail(Order order, List<OrderDetailServiceDto> detailServiceDtoList){

        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderDetailServiceDto dto : detailServiceDtoList) {
            OrderDetail orderDetail = OrderDetail.builder()
                    .price(dto.getPrice())
                    .calPrice(dto.getCalPrice())
                    .quantity(dto.getQuantity())
                    .food(dto.getFood())
                    .order(order)
                    .build();

            orderDetails.add(orderDetail);
            em.persist(orderDetail);
        }

        return orderDetails;
    }
}
