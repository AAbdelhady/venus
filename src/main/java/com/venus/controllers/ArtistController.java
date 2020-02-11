package com.venus.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.service.ArtistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public Page<ArtistResponse> searchArtists(Pageable pageable) {
        return artistService.searchArtists(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistResponse createArtist() {
        return artistService.createArtist();
    }

    @GetMapping("{id}")
    public ArtistResponse findArtistById(@PathVariable Long id) {
        return artistService.findArtistById(id);
    }
}
