package com.example.insta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.insta.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
