package com.example.dowebfinance.controller;

import com.example.dowebfinance.dtos.ExpenseRequestDTO;
import com.example.dowebfinance.dtos.ExpenseResponseDTO;
import com.example.dowebfinance.entity.UserEntity;
import com.example.dowebfinance.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // POST com authenticationprincipal /api/expenses
    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> criar(
            @Valid @RequestBody ExpenseRequestDTO dto,
            @AuthenticationPrincipal UserEntity usuarioLogado) {
        return new ResponseEntity<>(expenseService.salvarDespesa(dto, usuarioLogado), HttpStatus.CREATED);
    }

    // GET /api/expenses
    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> listar(
            @AuthenticationPrincipal UserEntity usuarioLogado) {
        return ResponseEntity.ok(expenseService.listarDespesasPorUsuario(usuarioLogado));
    }

    // GET /api/expenses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> buscarPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserEntity usuarioLogado) {
        return ResponseEntity.ok(expenseService.buscarDespesaPorId(id, usuarioLogado));
    }

    // PUT /api/expenses/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> editar(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDTO dto,
            @AuthenticationPrincipal UserEntity usuarioLogado) {
        return ResponseEntity.ok(expenseService.editarDespesas(id, dto, usuarioLogado));
    }

    // DELETE /api/expenses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id,
            @AuthenticationPrincipal UserEntity usuarioLogado) {
        expenseService.deletarDespesa(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }
}