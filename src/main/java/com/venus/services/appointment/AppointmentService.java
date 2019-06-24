package com.venus.services.appointment;

import com.venus.domain.dtos.appointment.AppointmentRequest;
import com.venus.domain.dtos.appointment.AppointmentResponse;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest request);
}
