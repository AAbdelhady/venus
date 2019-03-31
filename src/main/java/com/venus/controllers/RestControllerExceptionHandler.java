package com.venus.controllers;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.venus.domain.exceptions.ForbiddenException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {

    private static final String EXCEPTION_MESSAGE_ATTR = "exceptionMessage";
    private static final String VALIDATION_ERRORS_ATTR = "validationErrors";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Model model) {
        model.addAttribute(VALIDATION_ERRORS_ATTR, ex.getBindingResult().getAllErrors());
        return "400";
    }

    @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestExceptions(Exception ex, Model model) {
        setExceptionMessageInModel(ex, model);
        return "400";
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbiddenException(Exception ex, Model model) {
        setExceptionMessageInModel(ex, model);
        return "403";
    }

    private void setExceptionMessageInModel(Exception ex, Model model) {
        model.addAttribute(EXCEPTION_MESSAGE_ATTR, ex.getMessage());
    }
}
