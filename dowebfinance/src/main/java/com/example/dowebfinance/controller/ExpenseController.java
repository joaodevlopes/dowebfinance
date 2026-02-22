package com.example.dowebfinance.controller;

import com.example.dowebfinance.dtos.ExpenseRequestDTO;
import com.example.dowebfinance.dtos.ExpenseResponseDTO;
import com.example.dowebfinance.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    // POST /api/expenses
    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> criar(@Valid @RequestBody ExpenseRequestDTO dto){
       ExpenseResponseDTO novaDespesa = expenseService.salvarDespesa(dto);
       return new ResponseEntity<>(novaDespesa, HttpStatus.CREATED);
    }
    // GET /api/expenses/user/{userId}
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponseDTO>> listarPorUsuario(@PathVariable Long userId){
        List<ExpenseResponseDTO> despesas = expenseService.listarDespesasPorUsuario(userId);
        return ResponseEntity.ok(despesas);
    }

    // GET /api/expenses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> buscarPorId(@PathVariable Long id) {
        ExpenseResponseDTO despesa = expenseService.buscarDespesaPorId(id);
        return ResponseEntity.ok(despesa);
    }

    // PUT /api/expenses/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> editar(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequestDTO dto) {
        ExpenseResponseDTO despesaAtualizada = expenseService.editarDespesas(id, dto);
        return ResponseEntity.ok(despesaAtualizada);
    }

    // DELETE /api/expenses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        expenseService.deletarDespesa(id);
        return ResponseEntity.noContent().build();
    }
}
