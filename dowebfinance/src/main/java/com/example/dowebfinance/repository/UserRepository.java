package com.example.dowebfinance.repository;

import com.example.dowebfinance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // Para o login:
    Optional<UserEntity> findByEmail(String email);
}
