package com.venus.controllers;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

abstract class MvcTest {

    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    void init(Object controller) {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestControllerExceptionHandler())
                .build();
    }
}
