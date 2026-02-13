package com.example.dowebfinance.dtos;

import com.example.dowebfinance.enums.Category;
import com.example.dowebfinance.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequestDTO(
        @NotBlank(message = "A descrição é obrigatória")
        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String description,

        @NotNull(message = "O valor é obrigatório!")
        @Positive(message = "O valor deve ser positivo")
        BigDecimal value,

        @NotNull(message = "O tipo de transação é obrigatório")
        TransactionType type,

        @NotNull(message = "A categoria é obrigatória")
        Category category,

        @NotBlank(message = "O nome do banco é obrigatório!")
        String bankName,

        @NotNull(message = "A data da despesa é obrigatória")
        LocalDate expenseDate,

        @NotNull(message = "O ID do usuário é obrigatório")
        Long userId
) {}
