package com.tianchen.homehub_backend.model;

import java.time.LocalDate;

public record Calendar(
        Long id,
        String date,
        String time,
        String title
) {
    public Calendar {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be null");
        }
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
    }
}
