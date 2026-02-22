package com.example.dowebfinance.mapper;

import com.example.dowebfinance.dtos.UserRequestDTO;
import com.example.dowebfinance.dtos.UserResponseDTO;
import com.example.dowebfinance.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    // Converter o request (o que vai chegar) em Entidade (o que salva no banco de dados)
    UserEntity toEntity(UserRequestDTO request);

    // Converter a entidade (o que vem do banco) em Response (O que o usuário vê)
    UserResponseDTO toResponse(UserEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createAt", ignore = true)
    void updateEntityFromDto(UserRequestDTO dto, @MappingTarget UserEntity entity);


}
