package com.qfome.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qfome.dto.CategoriaDTO;
import com.qfome.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaDTO::new)
                .toList();
    }
}
