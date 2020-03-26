package com.venus.controllers;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.booking.core.dto.BookingResponse;
import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.calendar.dto.CalendarResponse;
import com.venus.feature.calendar.service.CalendarService;

import static com.venus.testutils.MapperTestUtils.appointmentMapper;
import static com.venus.testutils.MapperTestUtils.bookingMapper;
import static com.venus.testutils.UnitTestUtils.createDummyAppointment;
import static com.venus.testutils.UnitTestUtils.createDummyBooking;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CalendarControllerTest extends MvcTest {

    @InjectMocks
    private CalendarController controller;

    @Mock
    private CalendarService calendarService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        init(controller);
    }

    @Test
    public void listMyBookings_shouldReturnOk_whenRequestValid() throws Exception {
        // given
        Appointment appointment = createDummyAppointment();
        AppointmentResponse appointmentResponse = appointmentMapper.mapOne(appointment);

        Booking booking = createDummyBooking();
        BookingResponse bookingResponse = bookingMapper.mapOne(booking);

        given(calendarService.getMyCalendar()).willReturn(new CalendarResponse(Collections.singletonList(appointmentResponse), Collections.singletonList(bookingResponse)));

        // when
        mockMvc.perform(get("/calendar").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointments", hasSize(1)))
                .andExpect(jsonPath("$.appointments[0].id").value(appointment.getId()))
                .andExpect(jsonPath("$.appointments[0].artist.id").value(appointment.getArtist().getId()))
                .andExpect(jsonPath("$.appointments[0].customer.id").value(appointment.getCustomer().getId()))
                .andExpect(jsonPath("$.bookings", hasSize(1)))
                .andExpect(jsonPath("$.bookings[0].id").value(booking.getId()))
                .andExpect(jsonPath("$.bookings[0].artist.id").value(booking.getArtist().getId()))
                .andExpect(jsonPath("$.bookings[0].customer.id").value(booking.getCustomer().getId()))
                .andExpect(jsonPath("$.bookings[0].message").value(booking.getMessage()));
    }
}