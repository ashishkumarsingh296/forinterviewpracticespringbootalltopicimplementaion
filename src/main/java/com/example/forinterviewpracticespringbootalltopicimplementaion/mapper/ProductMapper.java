package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.AddProductDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.ModifyProductDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ModifyProductDTO toDto(Product p) {
        if (p == null) return null;
        ModifyProductDTO dto = new ModifyProductDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setDescription(p.getDescription());
        dto.setPrice(p.getPrice());
        dto.setQuantity(p.getQuantity());
        return dto;
    }

    public Product toEntity(AddProductDTO dto) {
        if (dto == null) return null;
        Product p = new Product();
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setQuantity(dto.getQuantity());
        return p;
    }
}
