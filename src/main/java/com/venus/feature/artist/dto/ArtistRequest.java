package com.venus.feature.artist.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ArtistRequest {

    private boolean active;

    @NotBlank
    private String artistNick;
}
