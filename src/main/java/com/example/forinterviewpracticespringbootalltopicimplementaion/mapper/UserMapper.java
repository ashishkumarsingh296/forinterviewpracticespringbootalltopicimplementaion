package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;


import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.AddUserDto;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.ModifyUserDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.User;


import java.util.Collections;
import java.util.Optional;

public class UserMapper {
    public static ModifyUserDTO toDto(User u) {
        if (u == null) return null;
        return new ModifyUserDTO(u.getId(), u.getName(), u.getEmail(), u.getRoles());
    }

    public static User toEntity(AddUserDto dto) {
        if (dto == null) return null;
        User u = new User();
        u.setName(dto.getName());
        u.setEmail(dto.getEmail());
        u.setRoles(Collections.singleton(dto.getRole()));
        return u;
    }

}
