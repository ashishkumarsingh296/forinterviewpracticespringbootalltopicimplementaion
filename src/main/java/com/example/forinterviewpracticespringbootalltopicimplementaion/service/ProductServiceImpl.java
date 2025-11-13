package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.common.ActionConstants;
import com.example.forinterviewpracticespringbootalltopicimplementaion.constants.EntityConstants;
import com.example.forinterviewpracticespringbootalltopicimplementaion.customanotation.Auditable;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.AddProductDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.ModifyProductDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Product;
import com.example.forinterviewpracticespringbootalltopicimplementaion.exception.ResourceNotFoundException;
import com.example.forinterviewpracticespringbootalltopicimplementaion.mapper.ProductMapper;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final AuditLogService auditLogService;

    @Override
    @CacheEvict(value = "allProducts", allEntries = true)
    @Auditable(action = ActionConstants.CREATE, entity = EntityConstants.PRODUCT)
    public ModifyProductDTO create(@Valid AddProductDTO dto, String username) {
        Product product = mapper.toEntity(dto);
        // ✅ Explicitly call audit service here
//       auditLogService.log(username, "Product", "CREATE", "Created new product successfully",product.getId());

        return mapper.toDto(repository.save(product));
    }


    @Override
    @Cacheable(value = "products", key = "#id")
    public ModifyProductDTO getById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product" , id));
        return mapper.toDto(product);
    }


    @Override
    @Cacheable(value = "allProducts", key = "#pageable.pageNumber")
    public Page<ModifyProductDTO> getAll(Pageable pageable) {
        // Fetch paged data from repository
        Page<Product> productsPage = repository.findAll(pageable);

        // Convert entities to DTOs
        List<ModifyProductDTO> dtoList = productsPage.getContent()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, productsPage.getTotalElements());
    }

    @Override
    @CacheEvict(value = {"products", "allProducts"}, key = "#dto", allEntries = true)
    @Auditable(action = ActionConstants.UPDATE, entity = EntityConstants.PRODUCT)
    public ModifyProductDTO update(@Valid ModifyProductDTO dto) {
        Product product = repository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product" , dto.getId()));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());

        return mapper.toDto(repository.save(product));
    }

    @Override
    @CacheEvict(value = {"products", "allProducts"}, key = "#id", allEntries = true)
    @Auditable(action = ActionConstants.DELETE, entity = EntityConstants.PRODUCT)
    public ModifyProductDTO delete(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product" , id));
        repository.delete(product);
        ModifyProductDTO dto = mapper.toDto(product);
        return dto;
    }

}
