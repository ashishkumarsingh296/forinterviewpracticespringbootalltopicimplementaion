package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;


import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddUserDto {

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    private String email;

    // @NotBlank(message = "Password is mandatory")
    private String password;

    // @NotBlank(message = "Role is mandatory")
    private String role; // "ADMIN" or "USER" (optional)
}
