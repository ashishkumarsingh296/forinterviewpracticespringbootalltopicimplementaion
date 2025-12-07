package com.example.forinterviewpracticespringbootalltopicimplementaion.service;


import com.example.forinterviewpracticespringbootalltopicimplementaion.common.ActionConstants;
import com.example.forinterviewpracticespringbootalltopicimplementaion.constants.EntityConstants;
import com.example.forinterviewpracticespringbootalltopicimplementaion.customanotation.Auditable;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.AddUserDto;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.ModifyUserDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Role;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.User;
import com.example.forinterviewpracticespringbootalltopicimplementaion.exception.DuplicateResourceException;
import com.example.forinterviewpracticespringbootalltopicimplementaion.exception.ResourceNotFoundException;
import com.example.forinterviewpracticespringbootalltopicimplementaion.mapper.UserMapper;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.UserRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repo;
    public UserService(PasswordEncoder passwordEncoder, UserRepository repo) {
        this.passwordEncoder = passwordEncoder;
        this.repo = repo;
    }

    @Transactional
@Auditable(action = ActionConstants.CREATE, entity = EntityConstants.USER,isDeleted = ActionConstants.IS_DELETE)
public ModifyUserDTO create(AddUserDto dto) {

    if (dto.getEmail() != null && repo.existsByEmail(dto.getEmail())) {
        throw new DuplicateResourceException("Email already in use");
    }

    var role = ("ADMIN".equalsIgnoreCase(dto.getRole()))
            ? Role.ROLE_ADMIN
            : Role.ROLE_USER;

    User u = User.builder()
            .email(dto.getEmail())
            .name(dto.getName())
            .isDeleted(false)
            .password(passwordEncoder.encode(dto.getPassword()))
            .roles(Set.of(role))
            .build();


    User saved = repo.save(u);

    return UserMapper.toDto(saved);
}

    @Transactional(readOnly = true)
    public ModifyUserDTO getById(Long id) {
        User u = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User" , id));
        return UserMapper.toDto(u);
    }

    @Transactional(readOnly = true)
    public List<ModifyUserDTO> getAll() {
        return repo.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Auditable(action = ActionConstants.UPDATE, entity = EntityConstants.USER, isDeleted =ActionConstants.IS_DELETE)
    public ModifyUserDTO update(ModifyUserDTO dto) {
        User existing = repo.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException("User", dto.getId()));
        existing.setName(dto.getName());
        // check email change uniqueness
        if (dto.getEmail() != null && !dto.getEmail().equals(existing.getEmail())) {
            if (repo.existsByEmail(dto.getEmail())) {
                throw new IllegalArgumentException("Email already in use");
            }
            existing.setEmail(dto.getEmail());
        }

        existing.setRoles(dto.getRoles());


        User updated = repo.save(existing);
        return UserMapper.toDto(updated);
    }

    @Transactional
    @Auditable(action = ActionConstants.DELETE, entity = EntityConstants.USER,isDeleted= ActionConstants.IS_DELETE)
    public ModifyUserDTO delete(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User" , id));

        user.setIsDeleted(true);
        repo.delete(user);

        return UserMapper.toDto(user);
    }
//    public PageResponse<ModifyUserDTO> getAll(Pageable pageable) {
//        return repo.findAll(pageable).map(UserMapper::toDto);
//    }


    public PageResponse<ModifyUserDTO> getAllUsers(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> userPage = repo.findByIsDeletedFalse(pageable);

        List<ModifyUserDTO> users = userPage.getContent()
                .stream()
                .map(UserMapper::toDto)
                .toList();

        return new PageResponse<>(
                users,
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.getNumber()
        );
    }

}
