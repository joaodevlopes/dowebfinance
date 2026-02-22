package com.example.dowebfinance.filter;

import com.example.dowebfinance.service.CustomUserDetailsService;
import com.example.dowebfinance.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Pega o cabeçalho Authorization da requisição
        String authHeader = request.getHeader("Authorization");

        // Se não tem token ou não começa com "Bearer ", deixa passar
        // O SecurityConfig vai bloquear se a rota precisar de autenticação
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Remove o "Bearer " e fica só com o token
        String token = authHeader.substring(7);

        // Extrai o email do token
        String email = jwtService.extrairEmail(token);

        // Se tem email e ainda não está autenticado nessa requisição
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Busca o usuário no banco pelo email
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Valida o token
            if (jwtService.tokenValido(token, userDetails)) {

                // Cria o objeto de autenticação e registra no contexto do Spring
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Passa para o próximo filtro
        filterChain.doFilter(request, response);
    }
}

