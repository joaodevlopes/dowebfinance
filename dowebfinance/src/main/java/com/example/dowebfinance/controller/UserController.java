package com.example.dowebfinance.controller;

import com.example.dowebfinance.dtos.UserRequestDTO;
import com.example.dowebfinance.dtos.UserResponseDTO;
import com.example.dowebfinance.entity.UserEntity;
import com.example.dowebfinance.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> cadastrar(@Valid @RequestBody UserRequestDTO dto ){
        UserResponseDTO novoUsuario = userService.cadastrar(dto);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    // As rotas abaixo usam "/me": o usuário vem do token JWT, nunca da URL.
    // Assim ninguém consegue ler, editar ou apagar a conta de outra pessoa trocando o id.

    // GET /api/users/me
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> buscarMeusDados(@AuthenticationPrincipal UserEntity usuarioLogado){
        return ResponseEntity.ok(userService.buscarPorId(usuarioLogado.getId()));
    }

    // PUT /api/users/me
    @PutMapping("/me")
    public ResponseEntity<UserResponseDTO> atualizar(
            @Valid @RequestBody UserRequestDTO dto,
            @AuthenticationPrincipal UserEntity usuarioLogado){
        return ResponseEntity.ok(userService.atualizar(usuarioLogado.getId(), dto));
    }

    // DELETE /api/users/me
    @DeleteMapping("/me")
    public ResponseEntity<Void> deletar(@AuthenticationPrincipal UserEntity usuarioLogado){
        userService.deletarUsuario(usuarioLogado.getId());
        return ResponseEntity.noContent().build();
    }
}
