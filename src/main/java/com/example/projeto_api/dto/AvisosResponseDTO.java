package com.example.projeto_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvisosResponseDTO {
    private Long id;
    private String descricao;

    public AvisosResponseDTO(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
}