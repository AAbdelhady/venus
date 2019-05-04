package com.venus.domain.dtos.artist;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ArtistRequest {

    private boolean active;

    @NotBlank
    private String artistNick;
}
