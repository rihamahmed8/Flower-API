package com.example.flowerapi.controller;

import com.example.flowerapi.datatransferobject.CartItemResponse;
import com.example.flowerapi.datatransferobject.CartResponse;
import com.example.flowerapi.datatransferobject.FlowerResponse;
import com.example.flowerapi.entity.Cart;
import com.example.flowerapi.entity.CartItem;
import com.example.flowerapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;


    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        List<Cart> carts = cartService.findAll();

        // Check if there are no carts or all have empty items
        boolean allEmpty = carts.isEmpty() || carts.stream().allMatch(cart -> cart.getItems().isEmpty());

        if (allEmpty) {
            return ResponseEntity.ok("Cart is empty");
        }

        List<CartResponse> response = carts.stream()
                .map(cart -> new CartResponse(cart.getUser().getName(),
                        cart.getItems().stream()
                                .map(item -> new CartItemResponse(
                                        item.getFlower().getName(),
                                        item.getFlower().getColor(),
                                        item.getFlower().getPrice(),
                                        item.getQuantity(),
                                        item.getFlower().getImageUrl()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@RequestParam int userId,
                                            @RequestParam int flowerId,
                                            @RequestParam int quantity)
    {
    Cart cart = cartService.add(userId, flowerId, quantity);

    List<CartItemResponse> items = cart.getItems().stream().map(item ->
    {
        FlowerResponse flower = new FlowerResponse(
        item.getFlower().getName(),
        item.getFlower().getColor(),
        item.getFlower().getPrice(),
        item.getFlower().getImageUrl()
        );
        return  new CartItemResponse(flower, item.getQuantity());
    }).toList();
    CartResponse response = new CartResponse(cart.getUser().getName(), items);
    return ResponseEntity.ok(response);
    }
    @PatchMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestParam int cartItemId,
                                            @RequestParam int quantity)
    {
        try {
            Cart updateCart = cartService.update(cartItemId, quantity);

            CartItem updateItem = updateCart.getItems().stream()
                    .filter(item -> item.getCart_item_id() == cartItemId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Item not found in cart"));

            return ResponseEntity.ok(Map.of(
                    "message", "Item updated successfully",
                    "newQuantity", updateItem.getQuantity()
            ));
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
