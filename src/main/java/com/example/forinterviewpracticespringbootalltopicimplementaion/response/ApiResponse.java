package com.example.forinterviewpracticespringbootalltopicimplementaion.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {

    private int status;
    private String message;
    private String messageCode;
    private T data;
    private LocalDateTime timestamp;

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(int status, String message, String messageCode, T data) {
        this.status = status;
        this.message = message;
        this.messageCode = messageCode;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }


}
