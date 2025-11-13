package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.common.AppErrorCodesI;
import com.example.forinterviewpracticespringbootalltopicimplementaion.customanotation.RateLimit;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.AddProductDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.ModifyProductDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.response.ApiResponse;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.ProductService;
import com.example.forinterviewpracticespringbootalltopicimplementaion.utils.RestAPIStringMessageParser;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.*;
import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.SecurityExpressions.HAS_ANY_ROLE_USER_ADMIN;
import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.SecurityExpressions.HAS_ROLE_ADMIN;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
@SecurityRequirement(name = "bearerAuth")

@Tag(name = "product-controller", description = "Operations related to Products")
public class ProductController {

    private final ProductService service;
    private final RestAPIStringMessageParser restAPIStringParser;

@PreAuthorize(HAS_ROLE_ADMIN)
@PostMapping(CREATE_PRODUCT)
   @RateLimit(maxRequests = 5, timeWindow = 60)
   public ResponseEntity<ModifyProductDTO> create(@Valid @RequestBody AddProductDTO dto, Principal principal) {
       String username = principal.getName(); // JWT-authenticated username
        ModifyProductDTO response = service.create(dto,username);
        response.setStatus(HttpStatus.OK.value());
        response.setMessageCode(AppErrorCodesI.PRODUCT_CREATED_SUCCESS);
        response.setMessage(restAPIStringParser.getMessage( AppErrorCodesI.PRODUCT_CREATED_SUCCESS));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize(HAS_ANY_ROLE_USER_ADMIN)
    @GetMapping(PRODUCT_BY_ID)
    @RateLimit(maxRequests = 5, timeWindow = 60)
    public ResponseEntity<ModifyProductDTO> getById(@PathVariable Long id) {
        ModifyProductDTO product = service.getById(id);
        product.setStatus(HttpStatus.OK.value());
        product.setMessageCode(AppErrorCodesI.PRODUCT_FETCH_SUCCESS);
        product.setMessage(restAPIStringParser.getMessage(AppErrorCodesI.PRODUCT_FETCH_SUCCESS));
        return ResponseEntity.ok(product);
    }


    @GetMapping(GET_ALL_PRODUCTS)
    @PreAuthorize(HAS_ANY_ROLE_USER_ADMIN)
    @RateLimit(maxRequests = 5, timeWindow = 60)
    public ResponseEntity<ApiResponse<Page<ModifyProductDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort[0]).ascending());
        Page<ModifyProductDTO> products = service.getAll(pageable);
        String messageCode = products.isEmpty() ? AppErrorCodesI.NOPRODUCT_RECORDS_FOUND : AppErrorCodesI.PRODUCT_LIST_FETCH_SUCCESS;
        String message = restAPIStringParser.getMessage(messageCode);

        ApiResponse<Page<ModifyProductDTO>> response = new ApiResponse<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessageCode(messageCode);
        response.setMessage(message);
        response.setData(products);


        return ResponseEntity.ok(response);
    }



    @PostMapping(UPDATE_PRODUCT)
    @PreAuthorize(HAS_ANY_ROLE_USER_ADMIN)
    @RateLimit(maxRequests = 5, timeWindow = 60)
    public ResponseEntity<ModifyProductDTO> update(
            @Valid @RequestBody ModifyProductDTO dto) {
        ModifyProductDTO updated = service.update( dto);
        updated.setStatus(HttpStatus.OK.value());
        updated.setMessageCode(AppErrorCodesI.PRODUCT_UPDATED_SUCCESS);
        updated.setMessage(restAPIStringParser.getMessage(AppErrorCodesI.PRODUCT_UPDATED_SUCCESS));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(DELETE_PRODUCT)
    @PreAuthorize(HAS_ANY_ROLE_USER_ADMIN)
    @RateLimit(maxRequests = 5, timeWindow = 60)

    public ResponseEntity<ModifyProductDTO> delete(@PathVariable Long id) {
        ModifyProductDTO deleted = service.delete(id);
        deleted.setStatus(HttpStatus.OK.value());
        deleted.setMessageCode(AppErrorCodesI.PRODUCT_DELETE_SUCCESS);
        deleted.setMessage(restAPIStringParser.getMessage(AppErrorCodesI.PRODUCT_DELETE_SUCCESS));
        return ResponseEntity.ok(deleted);
    }

//    @PreAuthorize(HAS_ANY_ROLE_USER_ADMIN)
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> softDelete(@PathVariable Long id) {
//        productService.deactivateProduct(id);
//        return ResponseEntity.noContent().build();
//    }

}
