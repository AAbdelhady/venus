package com.venus.feature.booking.core.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BookingRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private Long artistId;

    @NotNull
    private Long SpecialityId;

    @NotNull
    private LocalDate bookingDate;

    private String message;
}
