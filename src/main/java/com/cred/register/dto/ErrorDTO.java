package com.cred.register.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorDTO {

    private LocalDateTime timestamp;
    private String path;
    private Integer status;
    private String error;
    private String message;

    public ErrorDTO(LocalDateTime now, int status, String error, String message, String path) {
        this.timestamp = now;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
