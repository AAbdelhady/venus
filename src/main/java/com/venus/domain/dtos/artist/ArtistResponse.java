package com.venus.domain.dtos.artist;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ArtistResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;
}
