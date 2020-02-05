package com.venus.exceptions;

import com.venus.exceptions.enums.ErrorCode;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class VenusException extends RuntimeException {
    protected ErrorCode code = ErrorCode.UNSPECIFIED;

    VenusException(String message) {
        super(message);
    }

    VenusException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }
}
