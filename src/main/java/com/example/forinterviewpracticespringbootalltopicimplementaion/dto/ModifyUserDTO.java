package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;


import com.example.forinterviewpracticespringbootalltopicimplementaion.common.BaseResponse;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ModifyUserDTO extends BaseResponse {


    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    private String email;

    private Set<Role> roles;
}
