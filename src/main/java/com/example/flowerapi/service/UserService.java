package com.example.flowerapi.service;

import com.example.flowerapi.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> findAll();
    boolean registerUser(String name,String email,String password);
    boolean authenticate(String email,String password);
    Optional<User> findById(int id);
}
