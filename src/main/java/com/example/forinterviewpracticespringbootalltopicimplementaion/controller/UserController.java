package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;//package com.example.springbootallmostfeaturedcovered.controller;


import com.example.forinterviewpracticespringbootalltopicimplementaion.common.AppErrorCodesI;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.AddUserDto;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.ModifyUserDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.response.ApiResponse;
import com.example.forinterviewpracticespringbootalltopicimplementaion.response.PageResponse;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.UserService;
import com.example.forinterviewpracticespringbootalltopicimplementaion.utils.RestAPIStringMessageParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.*;
import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.SecurityExpressions.HAS_ANY_ROLE_USER_ADMIN;


@RestController
@RequestMapping(BASE_PATH)
@SecurityRequirement(name = "bearerAuth")

public class UserController {

    private final UserService service;
    private final RestAPIStringMessageParser restAPIStringParser;

    public UserController(UserService service, RestAPIStringMessageParser restAPIStringParser) {
        this.service = service;
        this.restAPIStringParser = restAPIStringParser;
    }

    @PostMapping(CREATE_USER)
    @ResponseBody
    @PreAuthorize(HAS_ANY_ROLE_USER_ADMIN)
    public ResponseEntity<ModifyUserDTO> create(@Valid @RequestBody AddUserDto dto) {
        ModifyUserDTO response = service.create(dto);

        response.setStatus(HttpStatus.OK.value());
        response.setMessageCode(AppErrorCodesI.USER_CREATED_SUCCESS);
        response.setMessage(restAPIStringParser.getMessage( AppErrorCodesI.USER_CREATED_SUCCESS));
       return ResponseEntity.ok(response);

    }

    @GetMapping(USER_BY_ID)
    public ResponseEntity<ModifyUserDTO> getById(@PathVariable Long id) {
        ModifyUserDTO response=service.getById(id);

        response.setStatus(HttpStatus.OK.value());
        response.setMessageCode(AppErrorCodesI.USER_FETCH_SUCCESS);
        response.setMessage(restAPIStringParser.getMessage( AppErrorCodesI.USER_FETCH_SUCCESS));
        return ResponseEntity.ok(response);
    }


    @PreAuthorize(HAS_ANY_ROLE_USER_ADMIN)
    @GetMapping(GET_ALL_USERS)
    public ResponseEntity<ApiResponse<PageResponse<ModifyUserDTO>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        PageResponse<ModifyUserDTO> users =
                service.getAllUsers(page, size, sortBy, direction);

        ApiResponse<PageResponse<ModifyUserDTO>> response = new ApiResponse<>();
        String messageCode = users.isEmpty() ? AppErrorCodesI.NO_RECORDS_FOUND : AppErrorCodesI.USER_LIST_FETCH_SUCCESS;
        String message = restAPIStringParser.getMessage(messageCode);
        response.setStatus(HttpStatus.OK.value());
        response.setMessageCode(messageCode);
        response.setMessage(message);
        response.setData(users);

        return ResponseEntity.ok(response);

    }

    @PreAuthorize(HAS_ANY_ROLE_USER_ADMIN)
    @PostMapping(UPDATE_USER)
    public ResponseEntity<ModifyUserDTO> update(@Valid @RequestBody ModifyUserDTO dto) {
        ModifyUserDTO updated = service.update( dto);
        updated.setStatus(HttpStatus.OK.value());
        updated.setMessageCode(AppErrorCodesI.USER_UPDATED_SUCCESS);
        updated.setMessage(restAPIStringParser.getMessage(AppErrorCodesI.USER_UPDATED_SUCCESS));
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(DELETE_USER)
    @Operation(summary = "Delete user", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<ModifyUserDTO> delete(@PathVariable Long id) {
        ModifyUserDTO deleted = service.delete(id);
        deleted.setStatus(HttpStatus.OK.value());
        deleted.setMessageCode(AppErrorCodesI.USER_DELETE_SUCCESS);
        deleted.setMessage(restAPIStringParser.getMessage(AppErrorCodesI.USER_DELETE_SUCCESS));
        return ResponseEntity.ok(deleted);
    }

//    @PreAuthorize("hasAnyRole('ADMIN','USER')")
//    @GetMapping("/profile")
//    public ResponseEntity<?> getProfile(Authentication authentication) {
//        String email = authentication.getName();
//        return ResponseEntity.ok(service.getByEmail(email));
//    }
}
