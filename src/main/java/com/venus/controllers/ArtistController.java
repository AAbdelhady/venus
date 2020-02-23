package com.venus.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.venus.feature.artist.dto.ArtistRequest;
import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.dto.CategoryResponse;
import com.venus.feature.artist.service.ArtistService;
import com.venus.feature.common.dto.response.PageResponse;

import lombok.RequiredArgsConstructor;

import static com.venus.feature.localization.LocalizationConstants.ACCEPT_LANGUAGE_HEADER;
import static com.venus.feature.localization.LocalizationConstants.DEFAULT_LANG;

@RestController
@RequestMapping("artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public PageResponse<ArtistResponse> searchArtists(Pageable pageable) {
        return artistService.searchArtists(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistResponse createArtist(@RequestBody @Valid ArtistRequest request) {
        return artistService.createArtist(request);
    }

    @GetMapping("{id}")
    public ArtistResponse findArtistById(@PathVariable Long id) {
        return artistService.findArtistById(id);
    }


    @GetMapping("category")
    public List<CategoryResponse> listCategories(@RequestHeader(value = ACCEPT_LANGUAGE_HEADER, defaultValue = DEFAULT_LANG) String lang) {
        return artistService.listCategories(lang); // TODO ADD CACHE HEADER
    }
}
