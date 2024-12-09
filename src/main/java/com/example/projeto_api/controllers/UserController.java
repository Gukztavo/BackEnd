package com.example.projeto_api.controllers;


import com.example.projeto_api.domain.user.User;
import com.example.projeto_api.dto.ErroResponseDTO;
import com.example.projeto_api.dto.UserDTO;
import com.example.projeto_api.dto.UserResponseDTO;
import com.example.projeto_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UserController {
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("sucesso!");
    }
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> usuariosDTO = userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getNome(), user.getEmail(), user.getSenha()))
                .toList();
        return ResponseEntity.ok(usuariosDTO);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable String email, @RequestBody User updatedUser) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setNome(updatedUser.getNome());
            user.setSenha(updatedUser.getSenha()); // Certifique-se de usar hashing aqui
            userRepository.save(user);
            UserResponseDTO responseDTO = new UserResponseDTO(user.getNome(), user.getSenha());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @DeleteMapping("/{email}")
    public ResponseEntity<?> delete(@PathVariable String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        // Verifica se o usuário existe
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErroResponseDTO("Usuário não encontrado"));
        }
        // Exclui o usuário
        this.userRepository.deleteByEmail(user.get().getEmail());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
