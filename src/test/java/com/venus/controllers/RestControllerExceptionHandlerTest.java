package com.venus.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venus.exceptions.BadRequestException;
import com.venus.exceptions.ForbiddenException;
import com.venus.exceptions.NotFoundException;
import com.venus.exceptions.enums.ErrorCode;

import lombok.Data;
import lombok.NoArgsConstructor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestControllerExceptionHandlerTest extends MvcTest {

    @Before
    public void setUp() {
        DummyController controller = new DummyController();
        init(controller);
    }

    @Test
    public void handleIllegalArgumentException() throws Exception {
        // given
        final String message = "Illegal Argument Exception message!";

        // when
        mockMvc.perform(get("/dummy/IllegalArgumentException").content(message).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code.name").value(ErrorCode.UNSPECIFIED.name()))
                .andExpect(jsonPath("$.code.value").value(ErrorCode.UNSPECIFIED.getValue()))
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    public void handleMethodArgumentNotValidException() throws Exception {
        // given
        final DummyRequest request = new DummyRequest();

        // when
        mockMvc.perform(get("/dummy/MethodArgumentNotValidException").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code.name").value(ErrorCode.VALIDATION_ERROR.name()))
                .andExpect(jsonPath("$.code.value").value(ErrorCode.VALIDATION_ERROR.getValue()))
                .andExpect(jsonPath("$.message").value("Argument Validation Error(s)"))
                .andExpect(jsonPath("$.validationErrors[0].field").value("name"))
                .andExpect(jsonPath("$.validationErrors[0].rejectedValue").value("null"))
                .andExpect(jsonPath("$.validationErrors[0].message").value("must not be blank"));
    }

    @Test
    public void handleBadRequestException() throws Exception {
        // given
        final String message = "Bad request exception message!";

        // when
        mockMvc.perform(get("/dummy/BadRequestException").content(message).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code.name").value(ErrorCode.UNSPECIFIED.name()))
                .andExpect(jsonPath("$.code.value").value(ErrorCode.UNSPECIFIED.getValue()))
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    public void handleForbiddenException() throws Exception {
        // given
        final String message = "Forbidden exception message!";

        // when
        mockMvc.perform(get("/dummy/ForbiddenException").content(message).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.code.name").value(ErrorCode.UNSPECIFIED.name()))
                .andExpect(jsonPath("$.code.value").value(ErrorCode.UNSPECIFIED.getValue()))
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    public void handleNotFoundException() throws Exception {
        // given
        final String message = "Not found exception message!";

        // when
        mockMvc.perform(get("/dummy/NotFoundException").content(message).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code.name").value(ErrorCode.UNSPECIFIED.name()))
                .andExpect(jsonPath("$.code.value").value(ErrorCode.UNSPECIFIED.getValue()))
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    public void handleInternalServerError() throws Exception {
        // when
        mockMvc.perform(get("/dummy/ServerError").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code.name").value(ErrorCode.SERVER_ERROR.name()))
                .andExpect(jsonPath("$.code.value").value(ErrorCode.SERVER_ERROR.getValue()))
                .andExpect(jsonPath("$.message").value("Internal Server Error"));
    }

    @RestController
    @RequestMapping("dummy")
    class DummyController {

        @GetMapping("IllegalArgumentException")
        public void triggerIllegalArgumentException(@RequestBody String message) {
            throw new IllegalArgumentException(message);
        }

        @GetMapping("MethodArgumentNotValidException")
        public void triggerMethodArgumentNotValidException(@RequestBody @Valid DummyRequest request) {
        }

        @GetMapping("BadRequestException")
        public void triggerBadRequestException(@RequestBody String message) {
            throw new BadRequestException(message);
        }

        @GetMapping("ForbiddenException")
        public void triggerForbiddenException(@RequestBody String message) {
            throw new ForbiddenException(message);
        }

        @GetMapping("NotFoundException")
        public void triggerNotFoundException(@RequestBody String message) {
            throw new NotFoundException(message);
        }

        @GetMapping("ServerError")
        public void triggerServerError() {
            throw new RuntimeException();
        }
    }

    @Data
    @NoArgsConstructor
    private static class DummyRequest {
        @NotBlank
        private String name;
    }
}