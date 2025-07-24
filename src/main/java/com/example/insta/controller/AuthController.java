package com.example.insta.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.insta.model.Users;
import com.example.insta.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://vybe-e616b.web.app", allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // ✅ Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists.");
        }
        userRepository.save(user); // No password hashing yet
        return ResponseEntity.ok("User registered successfully.");
    }

    // ✅ Login and store user in session
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user, HttpSession session) {
        Optional<Users> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
            session.setAttribute("user", existingUser.get());
            return ResponseEntity.ok("Login successful.");
        }
        return ResponseEntity.status(401).body("Invalid username or password.");
    }

    // ✅ Logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully.");
    }

    // ✅ Get current logged-in user
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not logged in.");
        }
        return ResponseEntity.ok(user);
    }
}
