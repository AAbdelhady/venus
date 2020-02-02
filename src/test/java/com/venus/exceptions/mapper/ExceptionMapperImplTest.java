package com.venus.exceptions.mapper;

import org.junit.Test;

import com.venus.exceptions.BadRequestException;
import com.venus.exceptions.dto.ErrorResponse;
import com.venus.exceptions.enums.ErrorCode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ExceptionMapperImplTest {

    private ExceptionMapper mapper = new ExceptionMapperImpl();

    @Test
    public void mapOne_shouldMap_whenExceptionProvided() {
        // given
        BadRequestException exception = new BadRequestException(ErrorCode.UNSPECIFIED, "a message");

        // when
        ErrorResponse response = mapper.mapOne(exception);

        // then
        assertEquals(response.getMessage(), exception.getMessage());
        assertEquals(response.getCode().getName(), exception.getCode().name());
        assertEquals(response.getCode().getValue(), exception.getCode().getValue());
    }

    @Test
    public void mapOne_shouldMap_whenErrorCodeAndMessageProvided() {
        // given
        final String message = "a message";
        final ErrorCode errorCode = ErrorCode.SERVER_ERROR;

        // when
        ErrorResponse response = mapper.mapOne(errorCode, message);

        // then
        assertEquals(response.getMessage(), message);
        assertEquals(response.getCode().getName(), errorCode.name());
        assertEquals(response.getCode().getValue(), errorCode.getValue());
    }

    @Test
    public void mapOne_shouldReturnEmptyResponse_whenNullsProvided() {
        // when
        ErrorResponse response = mapper.mapOne(null, null);

        // then
        assertNull(response.getMessage());
        assertEquals(response.getCode().getName(), ErrorCode.UNSPECIFIED.name());
        assertEquals(response.getCode().getValue(), ErrorCode.UNSPECIFIED.getValue());
    }
}