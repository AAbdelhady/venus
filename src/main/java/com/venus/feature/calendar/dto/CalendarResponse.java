package com.venus.feature.calendar.dto;

import java.util.List;

import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.booking.core.dto.BookingResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalendarResponse {
    List<AppointmentResponse> appointments;
    List<BookingResponse> bookings;
}
