package com.venus.feature.booking.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.venus.feature.artist.dto.response.ArtistResponse;
import com.venus.feature.booking.entity.BookingStatus;
import com.venus.feature.customer.dto.CustomerResponse;
import com.venus.feature.specialty.dto.SpecialityResponse;

import lombok.Data;

@Data
public class BookingResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("customer")
    private CustomerResponse customer;

    @JsonProperty("artist")
    private ArtistResponse artist;

    @JsonProperty("speciality")
    private SpecialityResponse speciality;

    @JsonProperty("bookingDate")
    private LocalDate bookingDate;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private BookingStatus status;
}
