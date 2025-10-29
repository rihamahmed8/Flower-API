package com.example.flowerapi.controller;

import com.example.flowerapi.entity.Stock;
import com.example.flowerapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;
    @GetMapping("/getData")
    public List<Stock> getAllStocks() {
        return stockService.findAll();
    }
    @PostMapping("/add")
    public ResponseEntity<Stock> addStock(@RequestParam int flowerId,
                                          @RequestParam int quantity) {
        Stock stock = stockService.addStock(flowerId, quantity);
        return ResponseEntity.ok(stock);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStock(@PathVariable int id,
                                             @RequestParam int quantity)
    {
        Stock stock = new Stock();
        stock.setQuantity(quantity);
        Stock updated =  stockService.updateStock(id, stock);
        if(updated == null){
            return ResponseEntity.badRequest().body("Flower not found");
        }
        return ResponseEntity.ok("Stock updated successfully");
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStock(@PathVariable("id") int id) {
        stockService.deleteStock(id);
        return ResponseEntity.ok("Item Deleted Successfully");
    }
}
