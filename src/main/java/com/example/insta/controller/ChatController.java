package com.example.insta.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.insta.dto.WebSocketMessageDTO;
import com.example.insta.model.Message;
import com.example.insta.model.Users;
import com.example.insta.repository.MessageRepository;
import com.example.insta.repository.UserRepository;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/chat")
public void processMessage(WebSocketMessageDTO dto) {
    Users sender = userRepository.findByUsername(dto.getSender()).orElse(null);
    Users recipient = userRepository.findByUsername(dto.getRecipient()).orElse(null);

    if (sender == null || recipient == null) {
        return;
    }

    // Save to DB
    Message message = new Message();
    message.setSender(sender.getUsername());
    message.setReceiver(recipient.getUsername());
    message.setContent(dto.getContent());
    message.setTimestamp(LocalDateTime.now());
    Message saved = messageRepository.save(message);

    // Broadcast the full saved message (convert to DTO first)
    WebSocketMessageDTO outgoing = new WebSocketMessageDTO(
        saved.getSender(),
        saved.getReceiver(),
        saved.getContent(),
        saved.getTimestamp()
    );

    messagingTemplate.convertAndSend(
    "/topic/messages/" + recipient.getUsername(),
    outgoing
);

}


}
