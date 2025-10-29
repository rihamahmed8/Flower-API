package com.example.flowerapi.repository;

import com.example.flowerapi.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.concurrent.Flow;

public interface FlowerRepository extends JpaRepository<Flower,Integer> {
    List<Flower> findByNameContainingIgnoreCase(String name);
    List<Flower> findByColor(String color);
    List<Flower> findByPriceBetween(Float start,Float end);
}
