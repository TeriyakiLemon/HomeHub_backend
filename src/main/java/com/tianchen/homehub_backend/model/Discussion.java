package com.tianchen.homehub_backend.model;

import java.time.LocalDateTime;

public record Discussion(
        Long id,
        String title,
        String content,
        String author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
