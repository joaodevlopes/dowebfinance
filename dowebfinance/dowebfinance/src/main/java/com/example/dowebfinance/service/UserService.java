package com.example.dowebfinance.service;

import com.example.dowebfinance.entity.UserEntity;
import com.example.dowebfinance.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Cadastro de usuario:
    public UserEntity cadastrar(UserEntity usuario){
        Optional<UserEntity> usuarioExistente = userRepository.findByEmail(usuario.getEmail());

        if(usuarioExistente.isPresent()){
            throw new RuntimeException("Este e-mail já esta cadastrado!");
        }

        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);

        return userRepository.save(usuario);
    }

    //Buscar por ID:
    public UserEntity buscaPorId(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado!"));
    }

    //Buscar por email:
    public UserEntity buscarPorEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("E-mail não encontrado, digite um email válido!"));
    }

    //Atualizar dados
    public UserEntity atualizar(Long id, UserEntity dadosAtualizados){
        UserEntity usuario = buscaPorId(id);
        //Atualiza o nome:
        if(dadosAtualizados.getName() != null && !dadosAtualizados.getName().isEmpty()){
            usuario.setName(dadosAtualizados.getName());
        }
        //Atualiza senha:
        if(dadosAtualizados.getPassword()!= null && !dadosAtualizados.getPassword().isEmpty()){
            usuario.setPassword(passwordEncoder.encode(dadosAtualizados.getPassword()));
        }
        return userRepository.save(usuario);

    }

    //Deletar usúario:
     public void deletarUsuario(Long id){
        if(!userRepository.existsById(id)){
            throw new RuntimeException("Usuário não encontrado");
        }
         userRepository.deleteById(id);
     }




}
