package com.example.forinterviewpracticespringbootalltopicimplementaion.security;

import com.example.forinterviewpracticespringbootalltopicimplementaion.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ApiResponse<Object> api = new ApiResponse<>();
        api.setStatus(HttpServletResponse.SC_FORBIDDEN);
        api.setMessage("Access Denied");
        api.setMessageCode("ACCESS_DENIED");
        api.setData(null);
        api.setTimestamp(LocalDateTime.now());

        response.getWriter().write(mapper.writeValueAsString(api));
    }
}
