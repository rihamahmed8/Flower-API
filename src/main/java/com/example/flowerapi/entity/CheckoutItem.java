package com.example.flowerapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "checkout_items")
public class CheckoutItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "flower_id")
    private Flower flower;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "checkout_id")
    @JsonIgnore
    private Checkout checkout;

    public CheckoutItem() {
    }

    public CheckoutItem(Flower flower, int quantity, Checkout checkout) {
        this.flower = flower;
        this.quantity = quantity;
        this.checkout = checkout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }
}
