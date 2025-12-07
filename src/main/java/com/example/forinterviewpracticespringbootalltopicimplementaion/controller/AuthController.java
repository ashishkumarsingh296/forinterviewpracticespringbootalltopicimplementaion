package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.*;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Role;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.User;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.UserRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.security.JwtService;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.BackgroundTaskService;
import com.example.forinterviewpracticespringbootalltopicimplementaion.utils.AppLogger;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "auth-controller", description = "Operations related to login and reister user")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final Environment environment;

    @Autowired
    private AppLogger appLogger;

    @Autowired
    BackgroundTaskService backgroundTaskService;
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        var principal = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        Set<String> roles = principal.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toSet());

        String accessToken = jwtService.generateAccessToken(req.getEmail(), roles);
        String refreshToken = jwtService.generateRefreshToken(req.getEmail());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);


        appLogger.info("User logged in successfully: " + principal.getUsername());
        backgroundTaskService.sendEmailAsync(req.getEmail(), "Welcome!");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new AuthResponseDTO(accessToken, refreshToken, "Login successful", "Bearer", roles));
    }



    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO request) {

        String refreshToken = request.getRefreshToken();

        if (!jwtService.validateToken(refreshToken)) {
            return ResponseEntity.status(401)
                    .body(new AuthResponseDTO(null, null, "Invalid refresh token", null, null));
        }

        String email = jwtService.extractUsername(refreshToken);
        Set<String> roles = jwtService.getRoles(refreshToken);

        if (jwtService.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(401)
                    .body(new AuthResponseDTO(null, null, "Refresh token expired", null, null));
        }

        String newAccessToken = jwtService.generateAccessToken(email, roles);
        String newRefreshToken = jwtService.generateRefreshToken(email);

        return ResponseEntity.ok(new AuthResponseDTO(newAccessToken, newRefreshToken, "Token refreshed", "Bearer", roles));
    }

    @GetMapping("/hello")
    public String hello() {
        String response = "Response from server port: " + environment.getProperty("local.server.port");
        appLogger.info(response);
        return response;
    }


}
