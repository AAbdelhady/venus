package com.venus.feature.booking.offering.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class OfferingResponse {
    private Long id;
    private LocalTime time;
}
