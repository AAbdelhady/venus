package com.venus.feature.appointment.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.exceptions.NotFoundException;
import com.venus.feature.appointment.dto.AppointmentRequest;
import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.appointment.mapper.AppointmentMapper;
import com.venus.feature.appointment.repository.AppointmentRepository;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.booking.repository.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final BookingRepository bookingRepository;

    private final AppointmentMapper appointmentMapper;

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow(NotFoundException::new);
        Appointment appointment = saveAppointment(booking, request.getAppointmentTime());
        bookingRepository.deleteById(booking.getId());
        return appointmentMapper.mapOne(appointment);
    }

    private Appointment saveAppointment(Booking booking, OffsetDateTime appointmentTime) {
        Appointment appointment = new Appointment();
        appointment.setArtist(booking.getArtist());
        appointment.setCustomer(booking.getCustomer());
        appointment.setAppointmentTime(appointmentTime);
        return appointmentRepository.save(appointment);
    }
}
