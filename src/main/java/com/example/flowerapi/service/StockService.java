package com.example.flowerapi.service;

import com.example.flowerapi.entity.Stock;

import java.util.List;

public interface StockService {
    List<Stock> findAll();
    Stock addStock(int flowerId, int quantity);
    boolean deleteStock(int id);
    Stock updateStock(int id, Stock stock);
    void decreaseStock(int flowerId, int quantity);
}
