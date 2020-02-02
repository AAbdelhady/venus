package com.venus.exceptions;

import com.venus.exceptions.enums.ErrorCode;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class AbstractException extends RuntimeException {
    protected ErrorCode code = ErrorCode.UNSPECIFIED;

    AbstractException(String message) {
        super(message);
    }

    AbstractException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }
}
