package com.venus.feature.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.customer.dto.CustomerResponse;

import lombok.Data;

@Data
public class BookingResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("customer")
    private CustomerResponse customer;

    @JsonProperty("artist")
    private ArtistResponse artist;

    @JsonProperty("message")
    private String message;
}
