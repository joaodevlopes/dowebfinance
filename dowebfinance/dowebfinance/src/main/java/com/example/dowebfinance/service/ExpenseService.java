package com.example.dowebfinance.service;

import com.example.dowebfinance.dtos.ExpenseRequestDTO;
import com.example.dowebfinance.dtos.ExpenseResponseDTO;
import com.example.dowebfinance.entity.ExpenseEntity;
import com.example.dowebfinance.entity.UserEntity;
import com.example.dowebfinance.mapper.ExpenseMapper;
import com.example.dowebfinance.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final UserService userService;

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseMapper expenseMapper, UserService userService) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
        this.userService = userService;
    }

    // Salvar despesa (POST)
    @Transactional
    public ExpenseResponseDTO salvarDespesa(ExpenseRequestDTO dto) {

        UserEntity usuario = userService.buscarPorIdEntidade(dto.userId());

        ExpenseEntity despesa = expenseMapper.toEntity(dto);
        despesa.setUser(usuario);

        ExpenseEntity despesaSalva = expenseRepository.save(despesa);

        return expenseMapper.toResponse(despesaSalva);
    }

    // Listar despesas por usuário (GET)
    public List<ExpenseResponseDTO> listarDespesasPorUsuario(Long userId) {

        // Valida se o usuário existe antes de buscar as despesas
        userService.buscarPorIdEntidade(userId);

        List<ExpenseEntity> despesas = expenseRepository.findByUserId(userId);

        return despesas.stream()
                .map(expenseMapper::toResponse)
                .toList();
    }

    // Buscar despesa por id (GET)
    public ExpenseResponseDTO buscarDespesaPorId(Long id) {

        ExpenseEntity despesa = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada!"));

        return expenseMapper.toResponse(despesa);
    }

    // Editar despesa (PUT)
    @Transactional
    public ExpenseResponseDTO editarDespesas(Long id, ExpenseRequestDTO dto) {

        ExpenseEntity despesaExistente = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada para editar!"));

        // Se o userId mudou, busca o novo usuário e valida que ele existe
        UserEntity usuario = userService.buscarPorIdEntidade(dto.userId());

        expenseMapper.updateEntityFromDto(dto, despesaExistente);
        despesaExistente.setUser(usuario);

        ExpenseEntity despesaSalva = expenseRepository.save(despesaExistente);

        return expenseMapper.toResponse(despesaSalva);
    }

    // Deletar despesa (DELETE)
    @Transactional
    public void deletarDespesa(Long id) {
        ExpenseEntity despesa = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada para deletar!"));

        expenseRepository.delete(despesa);
    }
}
