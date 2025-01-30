package com.example.projeto_api.infra.security;
import com.example.projeto_api.domain.avisos.Avisos;
import com.example.projeto_api.domain.category.Category;
import com.example.projeto_api.dto.AvisosDto;
import com.example.projeto_api.dto.AvisosResponseDTO;
import com.example.projeto_api.repositories.AvisosRepositoiry;
import com.example.projeto_api.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvisosService {

    @Autowired
    private AvisosRepositoiry avisoRepository;

    @Autowired
    private CategoryRepository categoriaRepository;

    public Avisos criarAviso(AvisosDto avisoDTO) {
        Category categoria = categoriaRepository.findById(avisoDTO.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        Avisos aviso = new Avisos();
        aviso.setCategoria(categoria);
        aviso.setDescricao(avisoDTO.getDescricao());

        return avisoRepository.save(aviso);
    }

    public List<Avisos> listarAvisos() {
        return avisoRepository.findAll();
    }
    public List<AvisosResponseDTO> listarPorCategoria(Long idCategoria) {
        List<Avisos> avisos = avisoRepository.findByCategoriaId(idCategoria);
        return avisos.stream()
                .map(aviso -> new AvisosResponseDTO(aviso.getId(), aviso.getDescricao()))
                .collect(Collectors.toList());
    }
    public void deletarAviso(Long id) {
        // Verifica se o aviso existe
        Avisos aviso = avisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aviso não encontrado"));

//        // Verifica se o usuário tem permissão para deletar o aviso
//        if (!temPermissaoParaDeletar(aviso)) {
//            throw new RuntimeException("Você não tem permissão suficiente para performar esta ação");
//        }

        // Deleta o aviso
        avisoRepository.delete(aviso);
    }

    public Avisos atualizarAviso(Long id, String novaDescricao) {
        // Verifica se o aviso existe
        Avisos aviso = avisoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aviso não encontrado"));

        // Verifica se o usuário tem permissão para atualizar o aviso
//        if (!temPermissaoParaAtualizar(aviso)) {
//            throw new RuntimeException("Você não tem permissão suficiente para performar esta ação");
//        }

        // Atualiza a descrição
        aviso.setDescricao(novaDescricao);
        return avisoRepository.save(aviso);
    }

}
