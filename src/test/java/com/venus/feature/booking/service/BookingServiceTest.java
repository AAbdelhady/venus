package com.venus.feature.booking.service;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.booking.dto.BookingRequest;
import com.venus.feature.booking.dto.BookingResponse;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.booking.repository.BookingRepository;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.customer.repository.CustomerRepository;
import com.venus.feature.user.entity.User;

import static com.venus.testutils.MapperTestUtils.bookingMapper;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

    private BookingService service;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new BookingService(bookingRepository, artistRepository, customerRepository, bookingMapper);
    }

    @Test
    public void createBookingTest_success() {
        // given
        final long artistId = 10L;
        final long customerId = 11L;
        final String bookingMessage = "booking-message";

        User artistUser = new User();
        artistUser.setId(artistId);
        Artist artist = new Artist();
        ReflectionTestUtils.setField(artist, "id", artistId);
        artist.setUser(artistUser);
        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        User customerUser = new User();
        customerUser.setId(customerId);
        Customer customer = new Customer();
        ReflectionTestUtils.setField(customer, "id", customerId);
        customer.setUser(customerUser);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        when(bookingRepository.save(isA(Booking.class))).then(saveBookingAnswer());

        BookingRequest request = new BookingRequest();
        request.setArtistId(artistId);
        request.setCustomerId(customerId);
        request.setMessage(bookingMessage);

        // when
        BookingResponse response = service.createBooking(request);

        // then
        assertEquals(artistId, response.getArtist().getUser().getId().longValue());
        assertEquals(customerId, response.getCustomer().getUser().getId().longValue());

        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository, times(1)).save(bookingCaptor.capture());

        assertEquals(artistId, bookingCaptor.getValue().getArtist().getId().longValue());
        assertEquals(customerId, bookingCaptor.getValue().getCustomer().getId().longValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBookingTest_artistNotExist() {
        // given
        final long artistId = 10L;
        final long customerId = 11L;

        when(artistRepository.findById(artistId)).thenReturn(Optional.empty());

        User customerUser = new User();
        customerUser.setId(customerId);
        Customer customer = new Customer();
        customer.setUser(customerUser);
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        BookingRequest request = new BookingRequest();
        request.setArtistId(artistId);
        request.setCustomerId(customerId);

        // when
        service.createBooking(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createBookingTest_customerNotExist() {
        // given
        final long artistId = 10L;
        final long customerId = 11L;

        User artistUser = new User();
        artistUser.setId(artistId);
        Artist artist = new Artist();
        artist.setUser(artistUser);
        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        BookingRequest request = new BookingRequest();
        request.setArtistId(artistId);
        request.setCustomerId(customerId);

        // when
        service.createBooking(request);
    }

    private Answer<Booking> saveBookingAnswer() {
        return invocation -> (Booking) invocation.getArguments()[0];
    }
}