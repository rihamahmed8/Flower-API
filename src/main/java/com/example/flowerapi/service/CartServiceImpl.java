package com.example.flowerapi.service;

import com.example.flowerapi.entity.*;
import com.example.flowerapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    FlowerRepository flowerRepository;
    @Autowired
    StockRepository stockRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart add(int userId, int flowerId, int quantity) {
        //check user
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        //create cart
        Cart cart = cartRepository.findUserById(userId)
                .orElseGet(()-> cartRepository.save(new Cart(user)));
        //check flower and stock
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(()-> new RuntimeException("Flower not found"));
        Stock stock = stockRepository.findById(flowerId)
                .orElseThrow(()-> new RuntimeException("Stock not found"));
        if (stock.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock for this flower ");
        }
        //create item
        CartItem item = new CartItem(cart,flower,quantity);
        cartItemRepository.save(item);
        cart.getItems().add(item);
        return cartRepository.findById(cart.getId()).get();
    }

    @Override
    public void delete(int cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cart cart = item.getCart();
        if (cart != null) {
            cart.getItems().remove(item); // remove from cart list
        }

        cartItemRepository.delete(item);
    }

    @Override
    public Cart update(int cartItemId, int newQuantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(()-> new RuntimeException("Cart Item not found"));
        Stock stock = stockRepository.findById(cartItem.getFlower().getId())
                .orElseThrow(()-> new RuntimeException("Stock not found for this flower "));
        if (stock.getQuantity() < newQuantity) {
            throw new RuntimeException("Not enough stock for this flower ");
        }
        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
        return cartItem.getCart();
    }
}
