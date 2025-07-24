// package com.example.insta.controller;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.insta.dto.MessageRequest;
// import com.example.insta.model.Message;
// import com.example.insta.model.Users;
// import com.example.insta.repository.MessageRepository;
// import com.example.insta.repository.UserRepository;

// import jakarta.servlet.http.HttpSession;

// @RestController
// @RequestMapping("/api/messages")
// @CrossOrigin(origins = "https://vybe-e616b.web.app", allowCredentials = "true")
// public class MessageController {

//     @Autowired
//     private MessageRepository messageRepository;

//     @Autowired
//     private UserRepository userRepository;

//     // ✅ Send a message
//    @PostMapping("/send")
// public ResponseEntity<?> sendMessage(
//         HttpSession session,
//         @RequestBody MessageRequest request
// ) {
//     Users sender = (Users) session.getAttribute("user");
//     if (sender == null) {
//         return ResponseEntity.status(401).body("User not logged in");
//     }

//     Optional<Users> recipientOpt = userRepository.findByUsername(request.getRecipientUsername());
//     if (recipientOpt.isEmpty()) {
//         return ResponseEntity.badRequest().body("Recipient user not found");
//     }

//     Message message = new Message();
//     message.setSender(sender);
//     message.setRecipient(recipientOpt.get());
//     message.setContent(request.getContent());
//     message.setTimestamp(LocalDateTime.now());

//     messageRepository.save(message);
//     return ResponseEntity.ok("Message sent successfully");
// }


// @GetMapping("/chat/{username}")
// public ResponseEntity<?> getChat(HttpSession session, @PathVariable String username) {
//     Users currentUser = (Users) session.getAttribute("user");

//     if (currentUser == null) {
//         return ResponseEntity.status(401).body("User not logged in");
//     }

//     Optional<Users> otherUserOpt = userRepository.findByUsername(username);
//     if (otherUserOpt.isEmpty()) {
//         return ResponseEntity.badRequest().body("User not found");
//     }

//     Users otherUser = otherUserOpt.get();

//     List<Message> allMessages = messageRepository.findAll(); // fetch all
//     List<Message> conversation = allMessages.stream()
//         .filter(m ->
//             (m.getSender().getId().equals(currentUser.getId()) && m.getRecipient().getId().equals(otherUser.getId())) ||
//             (m.getSender().getId().equals(otherUser.getId()) && m.getRecipient().getId().equals(currentUser.getId()))
//         )
//         .sorted((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()))
//         .toList();



//     return ResponseEntity.ok(conversation);
// }


// }
