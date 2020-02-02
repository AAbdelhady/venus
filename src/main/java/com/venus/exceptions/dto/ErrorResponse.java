package com.venus.exceptions.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private ErrorCodeResponse code;
    private String message;
}
