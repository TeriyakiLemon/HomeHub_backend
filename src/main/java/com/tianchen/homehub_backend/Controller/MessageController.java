package com.tianchen.homehub_backend.Controller;

import com.tianchen.homehub_backend.Service.MessageService;
import com.tianchen.homehub_backend.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PutMapping("/{messageId}/Read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long messageId) {
        messageService.markMessageAsRead(messageId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/Send")
    public ResponseEntity<Message> sendMessage(@RequestBody Map<String, Object> payload) {
        String senderUsername = payload.get("senderUsername").toString();
        String receiverUsername = payload.get("receiverUsername").toString();
        String content = payload.get("content").toString();

        Message message = messageService.sendMessage(senderUsername, receiverUsername, content);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{username1}/{username2}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String username1, @PathVariable String username2) {
        List<Message> messages = messageService.getMessages(username1, username2);
        if (messages.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/UnreadCount/{username}")
    public ResponseEntity<Integer> countUnreadMessages(@PathVariable String username) {
        return ResponseEntity.ok(messageService.countUnreadMessages(username));
    }
}
