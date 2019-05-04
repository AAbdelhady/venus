package com.venus.domain.dtos.booking;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BookingRequest {

    @JsonProperty("customer_id")
    @NotBlank
    private Long customerId;

    @JsonProperty("artist_id")
    @NotBlank
    private Long artistId;

    @NotBlank
    private String message;
}
