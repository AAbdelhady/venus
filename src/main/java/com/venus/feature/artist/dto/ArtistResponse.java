package com.venus.feature.artist.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.venus.feature.artist.entity.Category;
import com.venus.feature.user.dto.UserResponse;

import lombok.Data;

@Data
public class ArtistResponse {

    @JsonUnwrapped
    private UserResponse user;

    private Category category;
}
