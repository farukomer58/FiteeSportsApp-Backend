package com.fitee.fiteeApp.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
public class ExceptionDto {

    private Timestamp timestamp;
    private int status;
    private String reason;
    private String message;
    private String path;

    public ExceptionDto(HttpStatus status, String message, String path) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.status = status.value();
        this.reason = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
