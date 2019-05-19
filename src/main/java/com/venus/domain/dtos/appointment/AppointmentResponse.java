package com.venus.domain.dtos.appointment;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.venus.domain.dtos.artist.ArtistResponse;
import com.venus.domain.dtos.customer.CustomerResponse;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
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
