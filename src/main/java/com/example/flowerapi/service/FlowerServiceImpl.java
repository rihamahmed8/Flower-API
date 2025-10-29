package com.example.flowerapi.service;

import com.example.flowerapi.entity.Flower;
import com.example.flowerapi.repository.FlowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class FlowerServiceImpl implements FlowerService {
    @Autowired
     FlowerRepository flowerRepository;
    @Override
    public List<Flower> findAll() {
        return flowerRepository.findAll();
    }

    @Override
    public Optional<Flower> findById(int id) {
        return flowerRepository.findById(id);
    }

    @Override
    public Flower save(String name, String color, Float price, String image) {
        if (!flowerRepository.findByNameContainingIgnoreCase(name).isEmpty()) {
            throw new RuntimeException("Flower with this name already exists!");
        }
        return flowerRepository.save(new Flower(name, color, price, image));
    }

    @Override
    public String saveImage(MultipartFile imageFile) {
            try {
                // Create uploads folder if it doesn’t exist
                String folder = "uploads/";
                Files.createDirectories(Paths.get(folder));

                // Generate a unique file name
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

                // Save the file in the uploads folder
                Path path = Paths.get(folder + fileName);
                Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                // Return the file path (you’ll store this in the database)
                return folder + fileName;
            } catch (IOException e) {
                throw new RuntimeException("Error saving image: " + e.getMessage());
            }
    }


    @Override
    public Flower update(int id, Flower flower) {
        Optional<Flower> existingOpt = flowerRepository.findById(id);
        if (existingOpt.isPresent()) {
            Flower existing = existingOpt.get();
            if (flower.getName() != null) existing.setName(flower.getName());
            if (flower.getColor() != null) existing.setColor(flower.getColor());
            if (flower.getPrice() != null) existing.setPrice(flower.getPrice());
            if (flower.getImageUrl() != null) existing.setImageUrl(flower.getImageUrl());
            return flowerRepository.save(existing);
        }
        return null;
    }


    @Override
    public boolean delete(int id) {
        if(flowerRepository.existsById(id)){
            flowerRepository.deleteById(id);
            return true;
        }
        return false;
    }
    //Filtering
    @Override
    public List<Flower> findByName(String name) {
        return flowerRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Flower> findByColor(String color) {
        return flowerRepository.findByColor(color);
    }

    @Override
    public List<Flower> findByPriceBetween(Float start, Float end) {
        return flowerRepository.findByPriceBetween(start, end);
    }
}
