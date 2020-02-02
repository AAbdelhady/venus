package com.venus.feature.appointment.service;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import com.venus.exceptions.NotFoundException;
import com.venus.feature.appointment.dto.AppointmentRequest;
import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.appointment.repository.AppointmentRepository;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.booking.repository.BookingRepository;
import com.venus.feature.customer.entity.Customer;

import static com.venus.testutils.MapperTestUtils.appointmentMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummyBooking;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppointmentServiceTest {

    private AppointmentService service;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new AppointmentService(appointmentRepository, bookingRepository, appointmentMapper);
    }

    @Test
    public void createAppointment_shouldSucceed_whenRequestIsValid() {
        // given
        final long bookingId = 1L;
        final long artistId = 10L;
        final long customerId = 11L;

        Artist artist = createDummyArtist(artistId);
        Customer customer = createDummyCustomer(customerId);

        Booking booking = createDummyBooking(bookingId, artist, customer);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        when(appointmentRepository.save(isA(Appointment.class))).then(saveAppointmentAnswer());

        OffsetDateTime appointmentTime = OffsetDateTime.now();

        AppointmentRequest request = new AppointmentRequest();
        request.setAppointmentTime(appointmentTime);
        request.setBookingId(bookingId);

        // when
        AppointmentResponse response = service.createAppointment(request);

        // then
        assertEquals(appointmentTime, response.getAppointmentTime());
        assertEquals(artistId, response.getArtist().getUser().getId().longValue());
        assertEquals(customerId, response.getCustomer().getUser().getId().longValue());

        verify(bookingRepository).deleteById(eq(bookingId));

        ArgumentCaptor<Appointment> appointmentCaptor = ArgumentCaptor.forClass(Appointment.class);
        verify(appointmentRepository).save(appointmentCaptor.capture());

        assertEquals(appointmentTime, appointmentCaptor.getValue().getAppointmentTime());
        assertEquals(artistId, appointmentCaptor.getValue().getArtist().getId().longValue());
        assertEquals(customerId, appointmentCaptor.getValue().getCustomer().getId().longValue());
    }

    @Test(expected = NotFoundException.class)
    public void createAppointment_shouldThrowNotFoundException_whenBookingNotExist() {
        // given
        final long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // when
        service.createAppointment(new AppointmentRequest());
    }

    private Answer<Appointment> saveAppointmentAnswer() {
        return invocation -> (Appointment) invocation.getArguments()[0];
    }
}