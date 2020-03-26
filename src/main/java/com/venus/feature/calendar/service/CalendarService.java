package com.venus.feature.calendar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.appointment.service.AppointmentService;
import com.venus.feature.booking.core.dto.BookingResponse;
import com.venus.feature.booking.core.service.BookingService;
import com.venus.feature.calendar.dto.CalendarResponse;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CalendarService {

    private final UserService userService;
    private final AppointmentService appointmentService;
    private final BookingService bookingService;

    public CalendarResponse getMyCalendar() {
        User user = userService.findAuthorizedUser();
        List<AppointmentResponse> appointments = appointmentService.listAppointmentsByUser(user);
        List<BookingResponse> bookings = bookingService.listBookingsByUser(user);
        return new CalendarResponse(appointments, bookings);
    }
}
