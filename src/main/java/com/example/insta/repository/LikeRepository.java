package com.example.insta.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.insta.model.Like;
import com.example.insta.model.Post;
import com.example.insta.model.Users;

public interface LikeRepository extends JpaRepository<Like, Long> {
    long countByPost(Post post);
    Optional<Like> findByUserAndPost(Users user, Post post);
    void deleteByUserAndPost(Users user, Post post);
    long countByPostId(Long postId);
    boolean existsByPostIdAndUserId(Long postId, Long userId);

}
