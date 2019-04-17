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

import com.venus.domain.dtos.Artist.ArtistRequest;
import com.venus.domain.exceptions.ForbiddenException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/test")
@Slf4j
public class TestController {

    @GetMapping
    public String test() {
        return new Date().toString();
    }

    @GetMapping("secure")
    public String testSecured() {
        return new Date().toString();
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

    @GetMapping("redirect")
    public ResponseEntity redirect() throws URISyntaxException {
        URI uri = new URI("https://www.google.com");
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(uri).build();
    }
}
