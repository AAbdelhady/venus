package com.venus.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationErrorResponse {
    private String field;
    private String rejectedValue;
    private String message;
}
