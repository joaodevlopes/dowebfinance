package com.example.dowebfinance.dtos;

import com.example.dowebfinance.enums.Category;
import com.example.dowebfinance.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequestDTO(
       @NotBlank String description,
        @NotNull @Positive BigDecimal value,
        @NotNull TransactionType type,
        @NotNull Category category,
        @NotBlank String bankName,
        LocalDate expenseDate,
        @NotNull Long userId //Apenas o ID do usuário é passado
) {}
