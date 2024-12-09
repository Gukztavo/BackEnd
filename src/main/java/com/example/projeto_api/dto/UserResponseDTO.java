package com.example.projeto_api.dto;

public class UserResponseDTO {
    private String nome;
    private String senha;

    public UserResponseDTO(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }
}
