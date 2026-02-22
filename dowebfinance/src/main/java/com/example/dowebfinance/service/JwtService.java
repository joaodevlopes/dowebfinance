package com.example.dowebfinance.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    // Pega a chave secreta do application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    // Tempo de expiração do token: 2 horas em milissegundos
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 2;

    // Gera a chave criptográfica a partir da String secreta
    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    //Gera o token JWT com o email do usuário como "subject"
    //o Subject é o identificador principal dentro do token
    public String gerarToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername()) // quem é o dono do token (email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // quando expira
                .signWith(getSigningKey()) // assina com a chave secreta
                .compact(); // Transforma em String
    }

    //Extrai o email (subject) de dentro do token
    public String extrairEmail(String token){
        return Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    //Valida se o token é valido - verifica se o email bate se se não expirou
    public boolean tokenValido(String token, UserDetails userDetails){
        String email = extrairEmail(token);
        return email.equals(userDetails.getUsername()) && !tokenExpirado(token);
    }

    //Verifica se o token expirou
    private Boolean tokenExpirado(String token){
        Date expiration = Jwts.parser()
                .verifyWith((javax.crypto.SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
        return expiration.before(new Date());
    }



}
