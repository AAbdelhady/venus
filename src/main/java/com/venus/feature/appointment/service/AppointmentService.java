package com.venus.feature.appointment.service;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.exceptions.NotFoundException;
import com.venus.feature.appointment.dto.AppointmentRequest;
import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.appointment.mapper.AppointmentMapper;
import com.venus.feature.appointment.repository.AppointmentRepository;
import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.booking.core.repository.BookingRepository;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;

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

    public List<AppointmentResponse> listAppointmentsByUser(User user) {
        List<Appointment> appointments = user.getRole() == Role.ARTIST ? appointmentRepository.findAllByArtistId(user.getId()) : appointmentRepository.findAllByCustomerId(user.getId());
        return appointmentMapper.mapList(appointments);
    }
}
