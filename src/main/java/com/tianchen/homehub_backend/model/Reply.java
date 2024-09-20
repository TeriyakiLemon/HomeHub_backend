package com.tianchen.homehub_backend.model;

import java.time.LocalDateTime;

public record Reply(
        Long id,
        Long discussionId,
        String content,
        String author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
