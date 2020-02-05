package com.venus.exceptions.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorCodeResponse {
    private String name;
    private int value;
}
