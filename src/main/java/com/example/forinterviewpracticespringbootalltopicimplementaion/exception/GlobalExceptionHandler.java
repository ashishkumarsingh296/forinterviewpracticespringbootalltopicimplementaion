package com.example.forinterviewpracticespringbootalltopicimplementaion.exception;

import com.example.forinterviewpracticespringbootalltopicimplementaion.exception.ResourceNotFoundException;
import com.example.forinterviewpracticespringbootalltopicimplementaion.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiResponse<T> response = new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                "RESOURCE_NOT_FOUND",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleValidation(MethodArgumentNotValidException ex) {
        String errorMsg = ex.getBindingResult().getFieldError().getDefaultMessage();
        ApiResponse<T> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                errorMsg,
                "VALIDATION_ERROR",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public <T> ResponseEntity<ApiResponse<T>> handleDatabaseErrors(DataIntegrityViolationException ex) {
        ApiResponse<T> response = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Database constraint violated: " + ex.getMostSpecificCause().getMessage(),
                "DB_ERROR",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public <T> ResponseEntity<ApiResponse<T>> handleGeneral(Exception ex) {
        ApiResponse<T> response = new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                "INTERNAL_ERROR",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        response.setMessageCode("METHOD_NOT_ALLOWED");
        response.setMessage("Request method '" + ex.getMethod() + "' is not supported for this endpoint.");
        response.setData(null);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        ApiResponse<Object> b = new ApiResponse<>();
        b.setStatus(HttpStatus.FORBIDDEN.value());
        b.setMessage("Access Denied");
        b.setMessageCode("ACCESS_DENIED");
        b.setData(null);
        b.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(b);
    }
}
