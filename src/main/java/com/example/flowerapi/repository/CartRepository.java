package com.example.flowerapi.repository;

import com.example.flowerapi.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {
    Optional<Cart> findUserById(int userId);
}
