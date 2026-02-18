package com.example.dowebfinance.service;

import com.example.dowebfinance.dtos.UserRequestDTO;
import com.example.dowebfinance.dtos.UserResponseDTO;
import com.example.dowebfinance.entity.UserEntity;
import com.example.dowebfinance.mapper.UserMapper;
import com.example.dowebfinance.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    // Cadastro
    @Transactional
    public UserResponseDTO cadastrar(UserRequestDTO dto) {

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Este e-mail já está cadastrado");
        }

        UserEntity usuario = userMapper.toEntity(dto);

        usuario.setPassword(passwordEncoder.encode(dto.password()));

        UserEntity usuarioSalvo = userRepository.save(usuario);

        return userMapper.toResponse(usuarioSalvo);
    }

    // Buscar por id (para API)
    public UserResponseDTO buscarPorId(Long id) {
        return userMapper.toResponse(buscarPorIdEntidade(id));
    }

    // Buscar por id (uso interno)
    public UserEntity buscarPorIdEntidade(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Atualizar — email e senha são ignorados pelo mapper intencionalmente,
    // pois ambos têm regras de negócio que precisam ser tratadas aqui
    @Transactional
    public UserResponseDTO atualizar(Long id, UserRequestDTO dto) {

        UserEntity usuarioExistente = buscarPorIdEntidade(id);

        // Email: verificamos unicidade antes de atualizar manualmente,
        // pois o mapper o ignora (evita sobrescrever sem validação)
        if (!usuarioExistente.getEmail().equals(dto.email())) {
            if (userRepository.findByEmail(dto.email()).isPresent()) {
                throw new RuntimeException("Este e-mail já está em uso");
            }
            usuarioExistente.setEmail(dto.email());
        }

        // O mapper atualiza apenas: name (campos sem regra especial)
        userMapper.updateEntityFromDto(dto, usuarioExistente);

        // Senha: também ignorada pelo mapper, tratada aqui por precisar de hash
        if (dto.password() != null && !dto.password().isBlank()) {
            usuarioExistente.setPassword(passwordEncoder.encode(dto.password()));
        }

        UserEntity usuarioAtualizado = userRepository.save(usuarioExistente);

        return userMapper.toResponse(usuarioAtualizado);
    }

    // Deletar
    public void deletarUsuario(Long id) {

        UserEntity usuario = buscarPorIdEntidade(id);

        userRepository.delete(usuario);
    }
}
