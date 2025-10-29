package com.example.flowerapi.repository;

import com.example.flowerapi.entity.CheckoutItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutItemRepository extends JpaRepository<CheckoutItem,Integer> {
}
