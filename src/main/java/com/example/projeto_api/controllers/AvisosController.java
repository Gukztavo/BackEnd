package com.example.projeto_api.controllers;
import com.example.projeto_api.domain.avisos.Avisos;
import com.example.projeto_api.dto.AvisosDto;
import com.example.projeto_api.dto.AvisosResponseDTO;
import com.example.projeto_api.infra.security.AvisosService;
import com.example.projeto_api.repositories.AvisosRepositoiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/avisos")

public class AvisosController {

    @Autowired
    private AvisosService avisosService;

    @PostMapping
    public ResponseEntity<?> criarAviso(@RequestBody AvisosDto avisoDTO) {
        try {
            Avisos avisoCriado = avisosService.criarAviso(avisoDTO);
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Aviso criado com sucesso");
            return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Dados invalidos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
        }
    }


    @GetMapping("/{idCategoria}")
    public ResponseEntity<?> getAvisosPorCategoria(@PathVariable Long idCategoria) {
        try {
            List<AvisosResponseDTO> avisos = avisosService.listarPorCategoria(idCategoria);
            return ResponseEntity.ok(avisos); // 200 OK
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Dados invalidos");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
        }
    }

    // Deletar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAviso(@PathVariable Long id) {
        try {
            // Verifica se o ID é válido
            if (id == null || id <= 0) {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Dados invalidos");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
            }

            // Deleta o aviso
            avisosService.deletarAviso(id);

            // Retorna a resposta de sucesso
            Map<String, String> response = new HashMap<>();
//            response.put("mensagem", "Aviso deletado com sucesso");
            return ResponseEntity.ok(response); // 200 OK
        } catch (RuntimeException e) {
            // Trata erros específicos
            if (e.getMessage().equals("Aviso não encontrado")) {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Aviso não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404 Not Found
            } else if (e.getMessage().equals("Você não tem permissão suficiente para performar esta ação")) {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Você não tem permissão suficiente para performar esta ação");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response); // 403 Forbidden
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Dados invalidos");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAviso(@PathVariable Long id, @RequestBody AvisosDto avisoDTO) {
        try {
            // Verifica se a descrição foi fornecida
            if (avisoDTO.getDescricao() == null || avisoDTO.getDescricao().isEmpty()) {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Dados invalidos");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
            }

            // Atualiza o aviso
            Avisos avisoAtualizado = avisosService.atualizarAviso(id, avisoDTO.getDescricao());

            // Retorna a resposta de sucesso
            Map<String, String> response = new HashMap<>();
            response.put("descricao", avisoAtualizado.getDescricao());
            return ResponseEntity.ok(response); // 200 OK
        } catch (RuntimeException e) {
            // Trata erros específicos
            if (e.getMessage().equals("Aviso não encontrado")) {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Aviso não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // 404 Not Found
            } else if (e.getMessage().equals("Você não tem permissão suficiente para performar esta ação")) {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Você não tem permissão suficiente para performar esta ação");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response); // 403 Forbidden
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("mensagem", "Dados invalidos");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
            }
        }

    }
}
