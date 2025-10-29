package com.example.flowerapi.service;

import com.example.flowerapi.entity.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findAll();
    Cart add(int userId, int flowerId, int quantity);
    void delete(int cartItemId);
    Cart update(int cartItemId, int newQuantity);
}
