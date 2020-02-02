package com.venus.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venus.exceptions.ForbiddenException;
import com.venus.exceptions.NotFoundException;
import com.venus.feature.artist.dto.ArtistRequest;
import com.venus.feature.user.dto.UserResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @GetMapping
    public String test() {
        return new Date().toString();
    }

    @PostMapping
    public UserResponse testPost(@RequestBody UserResponse dto) {
        log.debug(dto.toString());
        return dto;
    }

    @GetMapping("secure")
    public Date testSecured() {
        return new Date();
    }

    @GetMapping("error/400")
    public void error400() {
        throw new IllegalArgumentException("Some Illegal Argument Exception Message!");
    }

    @PostMapping("error/args")
    public void errorArgs(@RequestBody @Valid ArtistRequest request) {
        log.info(request.toString());
    }

    @GetMapping("error/403")
    public void error403() {
        throw new ForbiddenException("Some Forbidden Exception Message!");
    }

    @GetMapping("error/404")
    public void error404() {
        throw new NotFoundException("What you requested does not exist!");
    }

    @GetMapping("redirect")
    public ResponseEntity redirect() throws URISyntaxException {
        URI uri = new URI("https://www.google.com");
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(uri).build();
    }
}
