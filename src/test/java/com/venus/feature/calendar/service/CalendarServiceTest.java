package com.venus.feature.calendar.service;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.appointment.service.AppointmentService;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.booking.core.dto.BookingResponse;
import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.booking.core.service.BookingService;
import com.venus.feature.calendar.dto.CalendarResponse;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.user.service.UserService;

import static com.venus.testutils.MapperTestUtils.appointmentMapper;
import static com.venus.testutils.MapperTestUtils.bookingMapper;
import static com.venus.testutils.UnitTestUtils.createDummyAppointment;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummyBooking;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CalendarServiceTest {

    private CalendarService calendarService;

    @Mock
    private UserService userService;
    @Mock
    private AppointmentService appointmentService;
    @Mock
    private BookingService bookingService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        calendarService = new CalendarService(userService, appointmentService, bookingService);
    }

    @Test
    public void getMyCalendar_shouldGetAppointmentsAndBookingsByUser() {
        // given
        Artist artist = createDummyArtist();
        Customer customer = createDummyCustomer();
        Appointment appointment = createDummyAppointment(artist, customer);
        Booking booking = createDummyBooking(artist, customer);

        when(userService.findAuthorizedUser()).thenReturn(artist.getUser());

        List<AppointmentResponse> appointmentResponses = appointmentMapper.mapList(Collections.singletonList(appointment));
        List<BookingResponse> bookingResponses = bookingMapper.mapList(Collections.singletonList(booking));
        when(appointmentService.listAppointmentsByUser(artist.getUser())).thenReturn(appointmentResponses);
        when(bookingService.listBookingsByUser(artist.getUser())).thenReturn(bookingResponses);

        // when
        CalendarResponse response = calendarService.getMyCalendar();

        // then
        assertEquals(response.getAppointments(), appointmentResponses);
        assertEquals(response.getBookings(), bookingResponses);
    }
}