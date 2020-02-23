package com.venus.feature.specialty.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SpecialityResponse {
    private Long id;
    private String name;
    private BigDecimal price;
}
