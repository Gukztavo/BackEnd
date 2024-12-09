package com.example.projeto_api.repositories;

import com.example.projeto_api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String>{
    Optional<User> findByEmail(String email);
    void deleteByEmail(String email);
}
