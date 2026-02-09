package com.example.dowebfinance.service;

import com.example.dowebfinance.entity.ExpenseEntity;
import com.example.dowebfinance.repository.ExpenseRepository;
import com.example.dowebfinance.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository){
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    //Salvar despesa:
    public ExpenseEntity salvarDespesa(ExpenseEntity despesa){
        if(despesa.getUser() == null || !userRepository.existsById(despesa.getUser().getId()) ){
            throw new RuntimeException("Usuário não encontrado para víncular despesa!");
        }
        return expenseRepository.save(despesa);
    }

    //Listar as despesas por usuario:
    public List<ExpenseEntity> listarDespesasPorUsuario(Long userId){
        List<ExpenseEntity> despesas = expenseRepository.findByUserId(userId);
        if(despesas.isEmpty()){
            System.out.println("Aviso: Este usuário não possui despesas cadastradas.");
        }
        return despesas;
    }

    //Buscar as despesas por id
    public ExpenseEntity buscarDespesaPorId(Long id){
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não tem despesa com esse ID!"));
    }


    //Deletar despesa
    public void deletarDespesa(Long id){
        if(!expenseRepository.existsById(id)){
            throw new RuntimeException("Não existe despesa a ser deletada!");
        }
        expenseRepository.deleteById(id);
    }


}
