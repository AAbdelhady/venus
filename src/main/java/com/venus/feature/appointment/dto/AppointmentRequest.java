package com.venus.feature.appointment.dto;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AppointmentRequest {

    @JsonProperty("booking_id")
    @NotNull
    private Long bookingId;

    @JsonProperty("appointment_time")
    @NotNull
    private OffsetDateTime appointmentTime;
}
