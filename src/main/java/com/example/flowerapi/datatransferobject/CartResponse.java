package com.example.flowerapi.datatransferobject;

import com.example.flowerapi.entity.CartItem;

import java.util.List;

public class CartResponse {
    private String username;
    private List<CartItemResponse> items; // can be List<CartItem> if you want exact type

    public CartResponse(String username, List<CartItemResponse> items) {
        this.username = username;
        this.items = items;
    }

    public String getUsername() {
        return username;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }
}
