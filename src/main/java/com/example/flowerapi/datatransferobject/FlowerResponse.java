package com.example.flowerapi.datatransferobject;

public class FlowerResponse {
    private String name;
    private String color;
    private Float price;
    private String imageUrl;

    public FlowerResponse(String name, String color, Float price, String imageUrl) {
        this.name = name;
        this.color = color;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Float getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
