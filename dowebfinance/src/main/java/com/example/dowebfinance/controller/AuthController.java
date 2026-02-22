package com.example.dowebfinance.controller;

import com.example.dowebfinance.dtos.AuthRequestDTO;
import com.example.dowebfinance.dtos.AuthResponseDTO;
import com.example.dowebfinance.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManagerm, JwtService jwtService){
        this.authenticationManager = authenticationManagerm;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO dto){
        // Entrega email + senha pro AuthenticationManager
        // Ele verifica no banco e compara a senha — se errado lança exceção
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        // Se chegou aqui está autenticado — pega o usuário e gera o token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.gerarToken(userDetails);
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
