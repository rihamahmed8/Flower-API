package com.example.flowerapi.service;

import com.example.flowerapi.entity.Flower;
import com.example.flowerapi.entity.Stock;
import com.example.flowerapi.repository.FlowerRepository;
import com.example.flowerapi.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private FlowerRepository flowerRepository;

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock addStock(int flowerId, int quantity) {
        Flower flower = flowerRepository.findById(flowerId)
                .orElseThrow(() -> new RuntimeException("Flower not found"));
        Stock stock = stockRepository.findByFlower_Id(flowerId)
                .orElse(new Stock(flower,0));
        stock.setQuantity(stock.getQuantity() + quantity);
        return stockRepository.save(stock);
    }

    @Override
    public boolean deleteStock(int id) {
        if(stockRepository.existsById(id)){
            stockRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Stock updateStock(int id, Stock stock) {
        Optional<Stock> stockOptional = stockRepository.findById(id);
        if(stockOptional.isPresent()){
            Stock existing = stockOptional.get();
           if (stock.getQuantity() > existing.getQuantity()) {
               existing.setQuantity(stock.getQuantity());
           }
            if(stock.getFlower() != null) existing.setFlower(stock.getFlower());
            return stockRepository.save(existing);
        }
        return null;
    }

    @Override
    public void decreaseStock(int flowerId, int quantity) {
        Stock stock = stockRepository.findByFlower_Id(flowerId)
                .orElseThrow(() -> new RuntimeException("Stock not found for this flower"));
        if (stock.getQuantity()<quantity) {
            throw new RuntimeException("Not enough stock for this flower");
        }
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
    }
}
