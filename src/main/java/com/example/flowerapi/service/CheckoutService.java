package com.example.flowerapi.service;

import com.example.flowerapi.entity.Checkout;

public interface CheckoutService {
    Checkout processCheckout(int userId);
}
