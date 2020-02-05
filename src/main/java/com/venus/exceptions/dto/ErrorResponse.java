package com.venus.exceptions.dto;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
    private ErrorCodeResponse code;
    private String message;
    private List<ValidationErrorResponse> validationErrors;
}
