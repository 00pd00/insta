package com.example.insta.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.insta.model.Like;
import com.example.insta.model.Post;
import com.example.insta.model.Users;
import com.example.insta.repository.LikeRepository;
import com.example.insta.repository.PostRepository;
import com.example.insta.repository.UserRepository;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin(origins = "http://localhost:5173")
public class LikeController {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<?> likeOrUnlikePost(@PathVariable Long postId, @PathVariable Long userId) {
        System.out.println("Toggling like for postId=" + postId + ", userId=" + userId);

        Optional<Users> user = userRepository.findById(userId);
        Optional<Post> post = postRepository.findById(postId);

        if (user.isEmpty() || post.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid user or post ID");
        }

        Optional<Like> existingLike = likeRepository.findByUserAndPost(user.get(), post.get());

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
        } else {
            Like like = new Like();
            like.setUser(user.get());
            like.setPost(post.get());
            likeRepository.save(like);
        }

        long likeCount = likeRepository.countByPost(post.get());
        return ResponseEntity.ok().body(likeCount);
    }

    @GetMapping("/{postId}")
        public ResponseEntity<Map<String, Object>> getLikeCountAndStatus(
                @PathVariable Long postId,
                @RequestParam Long userId) {

            long count = likeRepository.countByPostId(postId);
            boolean liked = likeRepository.existsByPostIdAndUserId(postId, userId);

            Map<String, Object> response = new HashMap<>();
            response.put("count", count);
            response.put("liked", liked);

            return ResponseEntity.ok(response);
        }



    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) {
        System.out.println("Received like count request for postId: " + postId);
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            System.out.println("Post not found with ID: " + postId);
            return ResponseEntity.badRequest().build();
        }
        long count = likeRepository.countByPost(post.get());
        System.out.println("Like count: " + count);
        return ResponseEntity.ok(count);
    }
}
