package com.venus.controllers;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.venus.exceptions.BadRequestException;
import com.venus.exceptions.ForbiddenException;
import com.venus.exceptions.NotFoundException;
import com.venus.exceptions.dto.ErrorResponse;
import com.venus.exceptions.enums.ErrorCode;
import com.venus.exceptions.mapper.ExceptionMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestControllerExceptionHandler {

    private static final String EXCEPTION_MESSAGE_ATTR = "exceptionMessage";
    private static final String VALIDATION_ERRORS_ATTR = "validationErrors";

    private final ExceptionMapper exceptionMapper;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, Model model) {
        model.addAttribute(VALIDATION_ERRORS_ATTR, ex.getBindingResult().getAllErrors());
        return "400";
    }

    @ExceptionHandler({ConstraintViolationException.class, IllegalArgumentException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequestExceptions(Exception raw) {
        BadRequestException ex = new BadRequestException(raw.getMessage());
        return exceptionMapper.mapOne(ex);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequestException(BadRequestException ex) {
        return exceptionMapper.mapOne(ex);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbiddenException(Exception ex, Model model) {
        setExceptionMessageInModel(ex, model);
        return "403";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(Exception ex, Model model) {
        setExceptionMessageInModel(ex, model);
        return "404";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleInternalServerErrorException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return exceptionMapper.mapOne(ErrorCode.SERVER_ERROR, "Internal Server Error");
    }

    private void setExceptionMessageInModel(Exception ex, Model model) {
        model.addAttribute(EXCEPTION_MESSAGE_ATTR, ex.getMessage());
    }
}
