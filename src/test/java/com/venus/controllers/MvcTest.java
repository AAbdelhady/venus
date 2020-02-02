package com.venus.controllers;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.venus.exceptions.mapper.ExceptionMapper;
import com.venus.exceptions.mapper.ExceptionMapperImpl;

abstract class MvcTest {

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    ExceptionMapper exceptionMapper = new ExceptionMapperImpl();

    void init(Object controller) {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestControllerExceptionHandler(exceptionMapper))
                .build();
    }
}
