package com.example.dowebfinance.dtos;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
   String message, //Mensagem do erro
   int status, // Código HTTP do erro (404, 400, 500, etc)
   LocalDateTime timestamp // Quando aconteceu
) {}
