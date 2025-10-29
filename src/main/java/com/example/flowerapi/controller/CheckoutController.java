package com.example.flowerapi.controller;

import com.example.flowerapi.entity.Checkout;
import com.example.flowerapi.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("{userId}")
    public ResponseEntity<?> processCheckout(@PathVariable int userId){
        try {
            Checkout checkout = checkoutService.processCheckout(userId);
            return ResponseEntity.ok(checkout);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
