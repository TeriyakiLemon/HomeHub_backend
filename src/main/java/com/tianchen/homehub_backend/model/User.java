package com.tianchen.homehub_backend.model;


public record User(
        Long id,
        String communityName,
        String username,
        String password,
        String email,
        String userType // Resident or property manager
) {
}
