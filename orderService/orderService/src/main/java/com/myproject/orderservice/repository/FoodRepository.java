package com.myproject.orderservice.repository;

import com.myproject.orderservice.dto.FoodRequestDto;
import com.myproject.orderservice.model.Food;
import com.myproject.orderservice.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class FoodRepository {
    @PersistenceContext
    private EntityManager em;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public FoodRepository(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    public Food findOne(Long foodId){
        return em.find(Food.class, foodId);
    }

    public List<Food> findAll(Long restaurantId){
        return em.createQuery("select f from Food f where f.restaurant.id=:restaurantId", Food.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }

    public List<Food> save(Long restaurantId, List<FoodRequestDto> requestDto) {
        Restaurant restaurant = restaurantRepository.findOne(restaurantId);
        List<Food> foods = new ArrayList<>();
        for(FoodRequestDto dto: requestDto){
            dto.setRestaurant(restaurant);
            Food food = new Food(dto);
            em.persist(food);
            foods.add(em.find(Food.class, food.getId()));
        }

        return foods;
    }
}
