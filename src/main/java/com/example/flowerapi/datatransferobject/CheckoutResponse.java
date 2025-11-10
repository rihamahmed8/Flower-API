package com.example.flowerapi.datatransferobject;


import java.time.LocalDateTime;
import java.util.List;

public class
CheckoutResponse {
    private String username;
    private double totalPrice;
    private LocalDateTime checkoutDate;
    private List<CartItemResponse> items;

    public CheckoutResponse( String username,double totalPrice, LocalDateTime checkoutDate, List<CartItemResponse> items) {
        this.username = username;
        this.totalPrice = totalPrice;
        this.checkoutDate = checkoutDate;
        this.items = items;
    }

    public String getUsername() {
        return username;
    }

    public double getTotalPrice() {
        return totalPrice;
    }


    public LocalDateTime getCheckoutDate() {
        return checkoutDate;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

}
