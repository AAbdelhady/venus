package com.venus.services.appointment;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import com.venus.domain.dtos.appointment.AppointmentRequest;
import com.venus.domain.dtos.appointment.AppointmentResponse;
import com.venus.domain.entities.Appointment;
import com.venus.domain.entities.Booking;
import com.venus.domain.entities.user.Artist;
import com.venus.domain.entities.user.Customer;
import com.venus.domain.entities.user.User;
import com.venus.domain.mappers.AppointmentMapper;
import com.venus.domain.mappers.AppointmentMapperImpl;
import com.venus.domain.mappers.ArtistMapperImpl;
import com.venus.domain.mappers.CustomerMapperImpl;
import com.venus.repositories.AppointmentRepository;
import com.venus.repositories.BookingRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppointmentServiceImplTest {

    private AppointmentService service;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        AppointmentMapper appointmentMapper = new AppointmentMapperImpl();
        ReflectionTestUtils.setField(appointmentMapper, "artistMapper", new ArtistMapperImpl());
        ReflectionTestUtils.setField(appointmentMapper, "customerMapper", new CustomerMapperImpl());
        service = new AppointmentServiceImpl(appointmentRepository, bookingRepository, appointmentMapper);
    }

    @Test
    public void createAppointmentTest_success() {
        // given
        final long bookingId = 1L;
        final long artistId = 10L;
        final long customerId = 11L;

        User artistUser = new User();
        artistUser.setId(artistId);
        Artist artist = new Artist();
        artist.setUser(artistUser);

        User customerUser = new User();
        customerUser.setId(customerId);
        Customer customer = new Customer();
        customer.setUser(customerUser);

        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setArtist(artist);
        booking.setCustomer(customer);
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
        assertEquals(artistId, response.getArtist().getId().longValue());
        assertEquals(customerId, response.getCustomer().getId().longValue());

        verify(bookingRepository, times(1)).deleteById(eq(bookingId));

        ArgumentCaptor<Appointment> appointmentCaptor = ArgumentCaptor.forClass(Appointment.class);
        verify(appointmentRepository, times(1)).save(appointmentCaptor.capture());

        assertEquals(appointmentTime, appointmentCaptor.getValue().getAppointmentTime());
        assertEquals(artistId, appointmentCaptor.getValue().getArtist().getId().longValue());
        assertEquals(customerId, appointmentCaptor.getValue().getCustomer().getId().longValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createAppointmentTest_bookingNotExist() {
        // given
        final long bookingId = 1L;
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        // when
        service.createAppointment(new AppointmentRequest());

        // then
        verify(bookingRepository, never()).deleteById(anyLong());
        verify(appointmentRepository, never()).save(isA(Appointment.class));
    }

    private Answer<Appointment> saveAppointmentAnswer() {
        return invocation -> (Appointment) invocation.getArguments()[0];
    }
}