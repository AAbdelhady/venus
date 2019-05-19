package com.venus.domain.mappers;

import org.mapstruct.Mapper;

import com.venus.domain.dtos.appointment.AppointmentRequest;
import com.venus.domain.dtos.appointment.AppointmentResponse;
import com.venus.domain.entities.Appointment;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentResponse toDto(Appointment entity);

    Appointment toEntity(AppointmentRequest appointmentRequest);
}
