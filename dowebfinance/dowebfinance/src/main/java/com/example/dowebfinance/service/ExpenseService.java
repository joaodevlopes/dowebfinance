package com.example.dowebfinance.service;

import com.example.dowebfinance.dtos.ExpenseRequestDTO;
import com.example.dowebfinance.dtos.ExpenseResponseDTO;
import com.example.dowebfinance.entity.ExpenseEntity;
import com.example.dowebfinance.mapper.ExpenseMapper;
import com.example.dowebfinance.repository.ExpenseRepository;
import com.example.dowebfinance.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.util.BeanUtil;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    @Autowired
    private ExpenseMapper expenseMapper;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository){
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    //Salvar despesa:(POST)
    public ExpenseResponseDTO salvarDespesa(ExpenseRequestDTO dto){
        //Converte Request que o usuário mandou em formato do banco no caso ENTITY
        ExpenseEntity despesa = expenseMapper.toEntity(dto);

        //Verifica se o dono da despesa existe
        if(!userRepository.existsById(dto.userId())){
            throw new RuntimeException("Usuário não encontrado");
        }

        //Salvar a enttidade no banco
        ExpenseEntity despesaSalva = expenseRepository.save(despesa);

        //Transforma a entity salva em um response para devolver pro usuário
        return expenseMapper.toResponse(despesaSalva);

    }

    //Listar as despesas por usuario:(GET)
    public List<ExpenseResponseDTO> listarDespesasPorUsuario(Long userId){

        List<ExpenseEntity> despesas = expenseRepository.findByUserId(userId);

        // Transformar a lista de entity em uma lista de ResponseDTO
        return despesas.stream()
                .map(expenseMapper::toResponse)
                .toList();
    }

    //Buscar as despesas por id (GET)
    public ExpenseResponseDTO  buscarDespesaPorId(Long id){

        ExpenseEntity despesa = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não tem despesas com esse ID!"));

        //Transformar em uma responseDto:
        return expenseMapper.toResponse(despesa);
    }

    //Editar Despesas (UPDATE)
    public ExpenseResponseDTO editarDespesas(Long id, ExpenseRequestDTO dto) {

        ExpenseEntity despesaExistente = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada para editar!"));

        expenseMapper.updateEntityFromDto(dto, despesaExistente);

        ExpenseEntity despesaSalva = expenseRepository.save(despesaExistente);

        return expenseMapper.toResponse(despesaSalva);

    }

    //Deletar despesa (DELETE)
    public void deletarDespesa(Long id){
        if(!expenseRepository.existsById(id)){
            throw new RuntimeException("Não existe despesa a ser deletada!");
        }
        expenseRepository.deleteById(id);
    }


}
