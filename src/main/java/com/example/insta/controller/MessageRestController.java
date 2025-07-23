package com.example.insta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.insta.model.Message;
import com.example.insta.repository.MessageRepository;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:5173")
public class MessageRestController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/{user1}/{user2}")
    public List<Message> getChatHistory(@PathVariable String user1, @PathVariable String user2) {
        return messageRepository.findChatHistory(user1, user2);
    }
}
