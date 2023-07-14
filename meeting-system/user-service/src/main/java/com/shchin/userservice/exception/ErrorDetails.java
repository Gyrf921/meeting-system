package com.shchin.userservice.exception;

import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorDetails {

    private int statusCode;

    private Date timestamp;

    private String message;

    private String details;

    public ErrorDetails(int statusCode, Date timestamp, String message, String details) {
        super();
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
