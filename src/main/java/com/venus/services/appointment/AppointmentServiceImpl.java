package com.venus.services.appointment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.domain.dtos.appointment.AppointmentRequest;
import com.venus.domain.dtos.appointment.AppointmentResponse;
import com.venus.domain.entities.Appointment;
import com.venus.domain.entities.Booking;
import com.venus.domain.mappers.AppointmentMapper;
import com.venus.repositories.AppointmentRepository;
import com.venus.repositories.BookingRepository;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final BookingRepository bookingRepository;

    private final AppointmentMapper appointmentMapper;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, BookingRepository bookingRepository, AppointmentMapper appointmentMapper) {
        this.appointmentRepository = appointmentRepository;
        this.bookingRepository = bookingRepository;
        this.appointmentMapper = appointmentMapper;
    }

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow(IllegalArgumentException::new);
        Appointment appointment = Appointment.builder().artist(booking.getArtist()).customer(booking.getCustomer()).appointmentTime(request.getAppointmentTime()).build();
        appointment = appointmentRepository.save(appointment);
        bookingRepository.deleteById(booking.getId());
        return appointmentMapper.toDto(appointment);
    }
}
