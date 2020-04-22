package com.venus.feature.artist.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.venus.feature.artist.entity.Category;
import com.venus.feature.specialty.dto.SpecialityResponse;
import com.venus.feature.user.dto.UserResponse;

import lombok.Data;

@Data
public class ArtistProfileResponse {
    @JsonUnwrapped
    private UserResponse user;

    private Category category;

    private List<SpecialityResponse> specialityList = new ArrayList<>();
}
