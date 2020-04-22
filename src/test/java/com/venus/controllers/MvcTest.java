package com.venus.controllers;

import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.venus.exceptions.mapper.ExceptionMapperImpl;

abstract class MvcTest {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected MockMvc mockMvc;

    void init(Object controller) {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestControllerExceptionHandler(new ExceptionMapperImpl()))
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }
}
