package com.tianchen.homehub_backend.Controller;

import com.tianchen.homehub_backend.Service.MessageService;
import com.tianchen.homehub_backend.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/{messageId}/Read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long messageId) {
        messageService.markMessageAsRead(messageId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/Send")
    public ResponseEntity<Message> sendMessage(@RequestBody Map<String, Object> payload) {
        Long senderId = Long.valueOf(payload.get("senderId").toString());
        Long receiverId = Long.valueOf(payload.get("receiverId").toString());
        String content = payload.get("content").toString();

        Message message = messageService.sendMessage(senderId, receiverId, content);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{userId1}/{userId2}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long userId1, @PathVariable Long userId2) {
        List<Message> messages = messageService.getMessages(userId1, userId2);
        return ResponseEntity.ok(messages);
    }
}
