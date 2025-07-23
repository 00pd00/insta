package com.example.insta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.insta.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
}
