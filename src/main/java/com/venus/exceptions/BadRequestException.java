package com.venus.exceptions;

import com.venus.exceptions.enums.ErrorCode;

import lombok.Getter;

@Getter
public class BadRequestException extends AbstractException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(ErrorCode code, String message) {
        super(code, message);
    }
}
