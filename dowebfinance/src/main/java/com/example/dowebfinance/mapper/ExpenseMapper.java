package com.example.dowebfinance.mapper;

import com.example.dowebfinance.dtos.ExpenseRequestDTO;
import com.example.dowebfinance.dtos.ExpenseResponseDTO;
import com.example.dowebfinance.entity.ExpenseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ExpenseMapper {
    // Converter o request (o que vai chegar) em Entidade (o que salva no banco de dados)
    // id é gerado pelo banco, user vem do token (definido no service) e createAt pelo @PrePersist
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    ExpenseEntity toEntity(ExpenseRequestDTO request);

    // Converter a entidade (o que vem do banco) em Response (O que o usuário vê)
    ExpenseResponseDTO toResponse(ExpenseEntity entity);

    // Metodo do update — mesmos campos protegidos do toEntity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    void updateEntityFromDto(ExpenseRequestDTO dto, @MappingTarget ExpenseEntity entity);

}
