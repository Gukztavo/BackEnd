package com.example.projeto_api.domain.category;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Certifique-se de que esta estratégia é apropriada para o banco.
    private Long id;

    @Column(name = "nome")
    private String nome;
}