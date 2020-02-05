package com.venus.feature.booking.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BookingRequest {

    @NotNull
    private Long customerId;

    @NotNull
    private Long artistId;

    private String message;
}
