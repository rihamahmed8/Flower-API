package com.example.flowerapi.service;

import com.example.flowerapi.entity.Role;
import com.example.flowerapi.entity.User;
import com.example.flowerapi.repository.RoleRepository;
import com.example.flowerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean registerUser(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists!");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(name, email, encodedPassword);

        Role userRole = roleRepository.findByName("Role_User")
                .orElseThrow(() -> new RuntimeException("Role User not found in database!"));
        user.setRoles(List.of(userRole));
        try {
            userRepository.save(user);
            return true;
        } catch (DataIntegrityViolationException e) {
            // Handle the database unique constraint for name
            throw new RuntimeException("Name already exists!");
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while registering the user!");
        }
    }



    @Override
    public boolean authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() &&
                passwordEncoder.matches(password,user.get().getPassword());
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
