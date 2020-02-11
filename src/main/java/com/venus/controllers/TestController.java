package com.venus.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.venus.exceptions.ForbiddenException;
import com.venus.exceptions.NotFoundException;
import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.mapper.ArtistMapper;
import com.venus.feature.user.dto.UserResponse;
import com.venus.test.service.DummyArtistService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final DummyArtistService dummyArtistService;
    private final ArtistMapper artistMapper;

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

    @GetMapping("artist")
    public List<ArtistResponse> insertRandomArtists(@RequestParam(value = "count", defaultValue = "20") int count) {
        return artistMapper.mapList(dummyArtistService.insertDummyArtists(count));
    }
}
