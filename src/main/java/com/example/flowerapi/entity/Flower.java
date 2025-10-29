package com.example.flowerapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Flower")
public class Flower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Color")
    private String color;
    @Column(name = "Price")
    private Float price;
    @Column(name ="Image")
    private String imageUrl;

    public Flower() {
    }

    public Flower(String name, String color, Float price, String imageUrl) {
        this.name = name;
        this.color = color;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
