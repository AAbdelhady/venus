package com.venus.feature.appointment.dto;

import java.time.LocalDateTime;

import com.venus.feature.artist.dto.response.ArtistResponse;
import com.venus.feature.customer.dto.CustomerResponse;

import lombok.Data;

@Data
public class AppointmentResponse {
    private Long id;
    private CustomerResponse customer;
    private ArtistResponse artist;
    private LocalDateTime appointmentTime;
}
