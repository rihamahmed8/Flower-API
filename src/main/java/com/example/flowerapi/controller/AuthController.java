package com.example.flowerapi.controller;

import com.example.flowerapi.entity.User;
import com.example.flowerapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password
    ) {
        try {
            boolean success = userService.registerUser(name, email, password);
            if (!success) {
                return ResponseEntity.badRequest().body("Email already exists");
            }
            return ResponseEntity.ok("User registered successfully");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        boolean authenticated = userService.authenticate(email, password);
        if (authenticated) {
            return ResponseEntity.ok("Logged in Successfully");
        }
        return ResponseEntity.badRequest().body("Invalid email or password");
    }
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") int id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}

