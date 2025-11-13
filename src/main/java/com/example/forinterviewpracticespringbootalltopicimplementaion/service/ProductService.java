package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.AddProductDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.ModifyProductDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ModifyProductDTO create(@Valid AddProductDTO dto, String username);

    ModifyProductDTO getById(Long id);

    Page<ModifyProductDTO> getAll(Pageable pageable);

    ModifyProductDTO update(@Valid ModifyProductDTO dto);

    ModifyProductDTO delete(Long id);
}
