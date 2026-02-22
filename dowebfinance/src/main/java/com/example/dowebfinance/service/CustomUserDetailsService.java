package com.example.dowebfinance.service;

import com.example.dowebfinance.exception.ResourceNotFoundException;
import com.example.dowebfinance.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // Spring chama esse metodo passando o email
    //voce busca no banco e devolve o usuario
    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException{
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
    }
}
