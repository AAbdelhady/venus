package com.venus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venus.domain.dtos.Artist.ArtistResponse;
import com.venus.services.artist.ArtistService;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("{id}")
    public ArtistResponse findArtistById(@PathVariable Long id) {
        return artistService.findArtistById(id);
    }
}
