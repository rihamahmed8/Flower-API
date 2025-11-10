package com.example.flowerapi.controller;

import com.example.flowerapi.datatransferobject.CartItemResponse;
import com.example.flowerapi.datatransferobject.CheckoutResponse;
import com.example.flowerapi.datatransferobject.FlowerResponse;
import com.example.flowerapi.entity.Checkout;
import com.example.flowerapi.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("{userId}")
    public ResponseEntity<?> processCheckout(@PathVariable int userId){
        try {
            Checkout checkout = checkoutService.processCheckout(userId);
            List<CartItemResponse> itemResponses = checkout.getItems().stream()
                    .map(item -> new CartItemResponse(
                            new FlowerResponse(
                                    item.getFlower().getName(),
                                    item.getFlower().getColor(),
                                    item.getFlower().getPrice(),
                                    item.getFlower().getImageUrl()
                            ),
                            item.getQuantity()
                    ))
                    .toList();
            CheckoutResponse response = new CheckoutResponse(
                    checkout.getUser().getUsername(),
                    checkout.getTotalPrice(),
                    checkout.getCheckoutDate(),
                    itemResponses
            );

            return ResponseEntity.ok(response);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
