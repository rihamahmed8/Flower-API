package com.example.flowerapi.repository;

import com.example.flowerapi.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock,Integer> {
    Optional<Stock> findByFlower_Id(int flowerId);
}
