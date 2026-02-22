package com.example.dowebfinance.dtos;

import com.example.dowebfinance.enums.Category;
import com.example.dowebfinance.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseResponseDTO(
        Long id,
        String description,
        BigDecimal value,
        TransactionType type,
        Category category,
        String bankName,
        LocalDate expenseDate,
        LocalDateTime createAt,
        UserResponseDTO user // Usa o DTO do usuário e não a Entity.
) {
}
