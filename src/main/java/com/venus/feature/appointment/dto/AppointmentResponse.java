package com.venus.feature.appointment.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.customer.dto.CustomerResponse;

import lombok.Data;

@Data
public class AppointmentResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("customer")
    private CustomerResponse customer;

    @JsonProperty("artist")
    private ArtistResponse artist;

    @JsonProperty("appointmentTime")
    private OffsetDateTime appointmentTime;
}
