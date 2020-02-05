package com.venus.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends VenusException {

    public NotFoundException(String message) {
        super(message);
    }
}
