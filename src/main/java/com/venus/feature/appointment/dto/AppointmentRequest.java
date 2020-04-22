package com.venus.feature.appointment.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AppointmentRequest {

    @NotNull
    private Long bookingId;

    @NotNull
    private Long offeringId;
}
