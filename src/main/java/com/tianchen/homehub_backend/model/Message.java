package com.tianchen.homehub_backend.model;

import java.time.LocalDateTime;

public record Message(
        Long id,
        User sender,
        User receiver,
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
