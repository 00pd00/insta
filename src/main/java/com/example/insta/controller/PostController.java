package com.example.insta.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.insta.model.Post;
import com.example.insta.model.Users;
import com.example.insta.repository.PostRepository;
import com.example.insta.repository.UserRepository;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "https://vybe-e616b.web.app" , allowCredentials="true")
public class PostController {

    // ✅ Use an absolute path for the uploads directory
    String UPLOAD_DIR = System.getProperty("user.dir") + File.separator + "uploads";

    @Autowired
    private PostRepository postRepository;

     @Autowired
    private UserRepository userRepository; // ✅ THIS IS REQUIRE

    @PostMapping("/create")
    public ResponseEntity<?> createPost(
        HttpSession session,
        @RequestParam("content") String content,
        @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        try {
            Users user = (Users) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(401).body("User not logged in");
            }

            Post post = new Post();
            post.setContent(content);
            post.setUser(user); // ✅ Tag the user

            if (image != null && !image.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) uploadPath.mkdirs();

                String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
                File dest = new File(uploadPath, filename);
                image.transferTo(dest);

                post.setImageUrl(filename);
            }

            postRepository.save(post);

            return ResponseEntity.ok("Post created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error: " + e.getMessage());
        }
    }





    // ✅ Get all posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
