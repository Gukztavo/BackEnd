package com.example.projeto_api.controllers;

import com.example.projeto_api.domain.user.Role;
import com.example.projeto_api.domain.user.User;
import com.example.projeto_api.dto.*;
import com.example.projeto_api.infra.security.TokenService;
import com.example.projeto_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class AuthControlller {

    @Autowired
    private final UserRepository repository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final TokenService tokenService;
    public AuthControlller(UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login") // se quiser alterar o nome do endpoint tem que alterar em filters tbm
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        if(user.getSenha().equals(body.senha())) { // Comparação direta sem hashing
            String token = this.tokenService.generateToken(user);
            return  ResponseEntity.ok(new LoginResponseDTO(token));
        }
        return ResponseEntity.badRequest().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/usuarios") // se quiser alterar o nome do endpoint tem que alterar em filters tbm
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByEmail(body.email());
    if (user.isEmpty()){
        User newUser = new User();
        newUser.setSenha(body.senha()); // Senha armazenada sem hashing (somente para testes)
        newUser.setEmail(body.email());
        newUser.setNome(body.nome());
        newUser.setAdmin(false);
        this.repository.save(newUser);
            String token = this.tokenService.generateToken(newUser);
            return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping ("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
