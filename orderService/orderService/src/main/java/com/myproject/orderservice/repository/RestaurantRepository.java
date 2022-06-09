package com.myproject.orderservice.repository;

import com.myproject.orderservice.dto.RestaurantDto;
import com.myproject.orderservice.model.Restaurant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

//JPA Repository를 사용하지 않음
@Repository
@Transactional
public class RestaurantRepository {

    // EntityManagerFactory에서 생성된 EntityManager 를 주입받음
    // EntityManagerFactory는 서비스별로 하나만 존재(싱글톤),
    // DB에 접근하는 트랜잭션이 생길 때마다 쓰레드 별로 EntityManager를 생성
    // EntityManager는 영속성 컨텍스트 내에서 Entity들을 관리함
    // 영속성 컨텍스트는 엔티티를 영구 저장(프로그램 종료시까지)하며,
    // DB에 보내기 전, 객체를 보관하는 가상의 DB 역할을 함.
    @PersistenceContext
    private EntityManager em;

    public List<Restaurant> findOneByName(String name){
        return em.createQuery("select r from Restaurant r where r.name=:name", Restaurant.class)
                .setParameter("name", name)
                .getResultList();
    }

    public Restaurant findOne(Long id) {
        return em.find(Restaurant.class, id);
    }

    public Restaurant[] findAll(){
        List<Restaurant> restaurantList = em.createQuery("select r from Restaurant r", Restaurant.class)
                .getResultList();
        Restaurant[] restaurants = new Restaurant[restaurantList.size()];
        int idx = 0;
        for(Restaurant restaurant:restaurantList){
            restaurants[idx] = restaurant;
            idx++;
        }
        return restaurants;
    }

    public Restaurant save(RestaurantDto requestDto){
        Restaurant restaurant = new Restaurant(requestDto);
        em.persist(restaurant);
        return em.find(Restaurant.class, restaurant.getId());
    }

}
