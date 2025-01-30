package com.example.projeto_api.repositories;
import com.example.projeto_api.domain.avisos.Avisos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvisosRepositoiry extends JpaRepository<Avisos, Long> {
    List<Avisos> findByCategoriaId(Long idCategoria);

}
