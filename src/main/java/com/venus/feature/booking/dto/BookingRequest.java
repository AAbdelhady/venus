package com.venus.feature.booking.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BookingRequest {

    @JsonProperty("customerId")
    @NotBlank
    private Long customerId;

    @JsonProperty("artistId")
    @NotBlank
    private Long artistId;

    @NotBlank
    private String message;
}
