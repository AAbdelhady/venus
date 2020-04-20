package com.venus.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.venus.feature.artist.dto.request.ArtistRequest;
import com.venus.feature.artist.dto.response.ArtistProfileResponse;
import com.venus.feature.artist.dto.response.ArtistResponse;
import com.venus.feature.artist.dto.response.CategoryResponse;
import com.venus.feature.artist.entity.Category;
import com.venus.feature.artist.service.ArtistService;
import com.venus.feature.common.dto.response.PageResponse;

import lombok.RequiredArgsConstructor;

import static com.venus.util.CacheControlUtils.maxAgeMinutes;

@RestController
@RequestMapping("artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public PageResponse<ArtistResponse> searchArtists(@RequestParam(required = false) Category category, Pageable pageable) {
        return artistService.searchArtists(category, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistResponse createArtist(@RequestBody @Valid ArtistRequest request) {
        return artistService.createArtist(request);
    }

    @GetMapping("{id}")
    public ArtistProfileResponse findArtistById(@PathVariable Long id) {
        return artistService.findArtistById(id);
    }


    @GetMapping("category")
    public ResponseEntity<List<CategoryResponse>> listCategories() {
        CacheControl cacheControl = maxAgeMinutes(60);
        List<CategoryResponse> response = artistService.listCategories();
        return ResponseEntity.ok().cacheControl(cacheControl).body(response);
    }
}
