package com.example.flowerapi.service;

import com.example.flowerapi.entity.Flower;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

public interface FlowerService {
    List<Flower> findAll();
    Optional<Flower> findById(int id);
    Flower save(String name, String color, Float price, String image);
    String saveImage(MultipartFile imageFile);
    Flower update(int id,Flower flower);
    boolean delete(int id);
    List<Flower> findByName(String name);
    List<Flower> findByColor(String color);
    List<Flower> findByPriceBetween(Float start,Float end);
}
