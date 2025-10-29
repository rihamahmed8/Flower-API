package com.example.flowerapi.service;

import com.example.flowerapi.entity.*;
import com.example.flowerapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CheckoutRepository checkoutRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CheckoutItemRepository checkoutItemRepository;

    @Override
    public Checkout processCheckout(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found"));
        Cart cart = cartRepository.findUserById(userId)
                .orElseThrow(()-> new RuntimeException("Cart not found"));
        if(cart.getItems().isEmpty()){
            throw new RuntimeException("Cart is empty - add flowers first!");
        }
        //prepare new checkout
        Checkout checkout = new Checkout();
        checkout.setUser(user);
        checkout.setCheckoutDate(LocalDateTime.now());

        List<CheckoutItem> checkoutItems = new ArrayList<>();
        double total = 0.0;
        //Loop through cart items
        for (CartItem item : cart.getItems()) {
            Stock stock = stockRepository.findById(item.getFlower().getId())
                    .orElseThrow(()-> new RuntimeException("Stock not found for flower:" +item.getFlower().getName()));
            if(stock.getQuantity() < item.getQuantity()){
                throw new RuntimeException("Not enough flower for: " + item.getFlower().getName());
            }
            //decrease stock
            stock.setQuantity(stock.getQuantity() - item.getQuantity());
            stockRepository.save(stock);

            //add to checkout
            CheckoutItem checkoutItem = new CheckoutItem(
                    item.getFlower(),item.getQuantity(),checkout);
            checkoutItems.add(checkoutItem);
            total += item.getFlower().getPrice() * item.getQuantity();
        }
        checkout.setItems(checkoutItems);
        checkout.setTotalPrice(total);

        //save checkout and items
        checkoutRepository.save(checkout);

        //clear the cart after checkout
        checkoutItemRepository.deleteAll(checkoutItems);
        cart.getItems().clear();
        cartRepository.save(cart);

        return checkout;
    }
}
