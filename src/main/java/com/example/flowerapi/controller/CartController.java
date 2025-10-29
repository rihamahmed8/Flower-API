package com.example.flowerapi.controller;

import com.example.flowerapi.entity.Cart;
import com.example.flowerapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;


    @GetMapping("/findAll")
    public List<Cart> findAll() {
        return cartService.findAll();
    }
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam int userId,
                                            @RequestParam int flowerId,
                                            @RequestParam int quantity)
    {
    Cart cart = cartService.add(userId, flowerId, quantity);
    return ResponseEntity.ok().body(cart);
    }
    @PatchMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateCartItem(@PathVariable int cartItemId,@RequestParam int quantity)
    {
        try {
            Cart updateCart = cartService.update(cartItemId, quantity);
            return ResponseEntity.ok().body(updateCart);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFromCart(@PathVariable("id") int id)
    {
        cartService.delete(id);
        return ResponseEntity.ok("Item Deleted Successfully");
    }
}
