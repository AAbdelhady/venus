package com.venus.exceptions.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SERVER_ERROR(-1),
    UNSPECIFIED(0),
    VALIDATION_ERROR(1);

    private final int value;
}
