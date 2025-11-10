package com.example.flowerapi.datatransferobject;

public class CartItemResponse {
    private FlowerResponse flower;
    private int quantity;

    public CartItemResponse(FlowerResponse flower, int quantity) {
        this.flower = flower;
        this.quantity = quantity;
    }


    public CartItemResponse(String name, String color, Float price, int quantity, String imageUrl) {
        this.flower = new FlowerResponse(name, color, price,imageUrl);
        this.quantity = quantity;
    }

    public FlowerResponse getFlower() {
        return flower;
    }

    public int getQuantity() {
        return quantity;
    }
}
