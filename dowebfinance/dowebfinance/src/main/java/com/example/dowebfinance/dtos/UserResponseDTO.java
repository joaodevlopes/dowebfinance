package com.example.dowebfinance.dtos;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        LocalDateTime createAt
)
{}