package com.example.flowerapi.controller;

import com.example.flowerapi.entity.Flower;
import com.example.flowerapi.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/flower")
public class FlowerController {
    @Autowired
    FlowerService flowerService;
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addFlower(
            @RequestParam String name,
            @RequestParam String color,
            @RequestParam Float price,
            @RequestParam("image") MultipartFile imageFile
    ) {
        try {
            String imageUrl = flowerService.saveImage(imageFile);
            Flower flower = flowerService.save(name, color, price, imageUrl);
            Map<String,Object> response = new HashMap<>();
            response.put("Message", "Flower has been added successfully");
            response.put("flower", flower);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
        }
    }
    @GetMapping("/flowers")
    public List<Flower> getAllFlowers() {
        return flowerService.findAll();
    }
    @GetMapping("/flower/{id}")
    public ResponseEntity<Flower> getFlower(@PathVariable("id") int id) {
        Optional<Flower> flower = flowerService.findById(id);
        return flower.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFlower(@PathVariable("id") int id) {
        flowerService.delete(id);
        return ResponseEntity.ok("Flower Deleted successfully");
    }
    @PatchMapping("/update/{id}")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFlower(
            @PathVariable int id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) Float price,
            @RequestParam(required = false) MultipartFile image
    ) {
        Flower flower = new Flower();
        flower.setName(name);
        flower.setColor(color);
        flower.setPrice(price);

        if (image != null && !image.isEmpty()) {
            String imageUrl = flowerService.saveImage(image);
            flower.setImageUrl(imageUrl);
        }

        Flower updated = flowerService.update(id, flower);
        if (updated == null) {
            return ResponseEntity.badRequest().body("Flower not found");
        }
        return ResponseEntity.ok("Flower updated successfully");
    }
    @GetMapping("/filter/name")
    public List<Flower> filterName(@RequestParam String name) {
        return flowerService.findByName(name);
    }
    @GetMapping("/filter/color")
    public List<Flower> filterColor(@RequestParam String color) {
        return flowerService.findByColor(color);
    }
    @GetMapping("/filter/price")
    public List<Flower> filterPrice(@RequestParam Float min,@RequestParam Float max) {
        return flowerService.findByPriceBetween(min,max);
    }
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get("uploads/").resolve(filename); // folder name
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // or detect automatically
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
