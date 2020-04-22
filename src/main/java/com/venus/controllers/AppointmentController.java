package com.venus.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.venus.feature.appointment.dto.AppointmentRequest;
import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponse createAppointment(@RequestBody @Valid AppointmentRequest request) {
        return appointmentService.createAppointment(request);
    }
}
