package com.venus.feature.artist.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.venus.feature.artist.entity.Category;
import com.venus.feature.specialty.dto.SpecialityRequest;

import lombok.Data;

@Data
public class ArtistRequest {

    @NotNull
    private Category category;

    @NotEmpty
    private List<@Valid @NotNull SpecialityRequest> specialities;
}
