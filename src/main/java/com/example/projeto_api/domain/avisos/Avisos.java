package com.example.projeto_api.domain.avisos;
import com.example.projeto_api.domain.category.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Avisos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Category categoria;

    @Column(name = "descricao")
    private String descricao;
}
