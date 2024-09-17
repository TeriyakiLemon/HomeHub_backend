package com.tianchen.homehub_backend.model;

import java.time.LocalDateTime;

public record Message(
        Long id,
        String senderUsername,
        String receiverUsername,
        String content,
        LocalDateTime timestamp,
        Boolean isRead
) {
    public Message {
        if (isRead == null) {
            isRead = false;
        }
    }
}
