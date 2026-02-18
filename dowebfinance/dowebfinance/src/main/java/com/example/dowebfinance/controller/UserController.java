package com.example.dowebfinance.controller;

import com.example.dowebfinance.dtos.UserRequestDTO;
import com.example.dowebfinance.dtos.UserResponseDTO;
import com.example.dowebfinance.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> buscarPorId (@PathVariable Long id){
        return ResponseEntity.ok(userService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UserRequestDTO dto ){
        return ResponseEntity.ok(userService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        userService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }



}
