package com.example.dowebfinance.entity;

import com.example.dowebfinance.enums.Category;
import com.example.dowebfinance.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(max = 255)
    @Column(length = 255)
    private String description;

    @NotNull(message = "O valor é obrigatório!")
    @Positive(message = "O valor deve ser maior que 0")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal value;

    @NotNull(message = "O tipo de transação é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @NotNull(message = "A categoria é orbigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Category category;

    @NotBlank(message = "O nome do banco é obrigatório!")
    @Column(nullable = false)
    private String bankName;

    @NotNull(message = "A data da despesa é obrigatória")
    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @PrePersist
    public void prePersist(){
        this.createAt = LocalDateTime.now();
        if(this.expenseDate == null){
            this.expenseDate = LocalDate.now();
        }
    }



}
