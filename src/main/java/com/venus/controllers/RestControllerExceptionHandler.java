package com.venus.controllers;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.venus.exceptions.BadRequestException;
import com.venus.exceptions.ForbiddenException;
import com.venus.exceptions.NotFoundException;
import com.venus.exceptions.dto.ErrorResponse;
import com.venus.exceptions.enums.ErrorCode;
import com.venus.exceptions.mapper.ExceptionMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestControllerExceptionHandler {

    private final ExceptionMapper exceptionMapper;

    @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestExceptions(Exception raw) {
        BadRequestException ex = new BadRequestException(raw.getMessage());
        return exceptionMapper.mapOne(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException raw) {
        return exceptionMapper.mapOne(ErrorCode.VALIDATION_ERROR, "Argument Validation Error(s)", raw.getBindingResult().getFieldErrors());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException ex) {
        return exceptionMapper.mapOne(ex);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(ForbiddenException ex) {
        return exceptionMapper.mapOne(ex);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException ex) {
        return exceptionMapper.mapOne(ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(Exception ex) {
        log.error(ex.getMessage(), ex);
        return exceptionMapper.mapOne(ErrorCode.SERVER_ERROR, "Internal Server Error");
    }
}
