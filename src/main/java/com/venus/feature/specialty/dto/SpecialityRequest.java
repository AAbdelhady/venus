package com.venus.feature.specialty.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SpecialityRequest {

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;
}
