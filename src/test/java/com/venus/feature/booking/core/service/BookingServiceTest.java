package com.venus.feature.booking.core.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import com.venus.exceptions.BadRequestException;
import com.venus.exceptions.NotFoundException;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.booking.core.dto.BookingRequest;
import com.venus.feature.booking.core.dto.BookingResponse;
import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.booking.core.entity.BookingStatus;
import com.venus.feature.booking.core.repository.BookingRepository;
import com.venus.feature.booking.offering.service.OfferingService;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.customer.repository.CustomerRepository;
import com.venus.feature.specialty.entity.Speciality;
import com.venus.feature.specialty.repository.SpecialityRepository;
import com.venus.feature.user.service.UserService;

import static com.venus.testutils.AssertionUtils.assertBookingEqualsResponse;
import static com.venus.testutils.MapperTestUtils.bookingMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummyBooking;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;
import static com.venus.testutils.UnitTestUtils.createDummySpeciality;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookingServiceTest {

    private BookingService service;

    @Mock
    private UserService userService;

    @Mock
    private OfferingService offeringService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SpecialityRepository specialityRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new BookingService(userService, offeringService, bookingRepository, artistRepository, customerRepository, specialityRepository, bookingMapper);
    }

    @Test
    public void createBooking_success() {
        // given
        final String bookingMessage = "booking-message";
        final LocalDate bookingDate = LocalDate.now();

        Artist artist = createDummyArtist();
        when(artistRepository.findById(artist.getId())).thenReturn(Optional.of(artist));

        Customer customer = createDummyCustomer();
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        Speciality speciality = createDummySpeciality(artist);
        when(specialityRepository.findById(speciality.getId())).thenReturn(Optional.of(speciality));

        when(bookingRepository.save(isA(Booking.class))).then(saveBookingAnswer());

        BookingRequest request = new BookingRequest();
        request.setArtistId(artist.getId());
        request.setCustomerId(customer.getId());
        request.setSpecialityId(speciality.getId());
        request.setMessage(bookingMessage);
        request.setBookingDate(bookingDate);

        // when
        BookingResponse response = service.createBooking(request);

        // then
        assertEquals(artist.getId(), response.getArtist().getUser().getId());
        assertEquals(customer.getId(), response.getCustomer().getUser().getId());
        assertEquals(speciality.getId(), response.getSpeciality().getId());
        assertEquals(BookingStatus.NEW, response.getStatus());
        assertEquals(bookingMessage, response.getMessage());
        assertEquals(bookingDate, response.getBookingDate());

        ArgumentCaptor<Booking> bookingCaptor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(bookingCaptor.capture());

        assertEquals(artist.getId(), bookingCaptor.getValue().getArtist().getId());
        assertEquals(customer.getId(), bookingCaptor.getValue().getCustomer().getId());
    }

    @Test(expected = NotFoundException.class)
    public void createBooking_shouldThrowNotFoundException_whenArtistNotExist() {
        // given
        final long artistId = 10L;

        when(artistRepository.findById(artistId)).thenReturn(Optional.empty());

        Customer customer = createDummyCustomer();
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        BookingRequest request = new BookingRequest();
        request.setArtistId(artistId);
        request.setCustomerId(customer.getId());

        // when
        service.createBooking(request);
    }

    @Test(expected = NotFoundException.class)
    public void createBooking_shouldThrowNotFoundException_whenCustomerNotExist() {
        // given
        final long customerId = 11L;

        Artist artist = createDummyArtist();
        when(artistRepository.findById(artist.getId())).thenReturn(Optional.of(artist));

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        BookingRequest request = new BookingRequest();
        request.setArtistId(artist.getId());
        request.setCustomerId(customerId);

        // when
        service.createBooking(request);
    }

    @Test
    public void listMyBooking_shouldInvokeGetArtistBookings_whenAuthorizedUserIsArtist() {
        // given
        Artist artist = createDummyArtist();
        Customer customer = createDummyCustomer();
        Booking booking = createDummyBooking(artist, customer);

        when(userService.findAuthorizedUser()).thenReturn(artist.getUser());
        when(bookingRepository.findAllByArtistId(artist.getId())).thenReturn(Collections.singletonList(booking));

        // when
        List<BookingResponse> responses = service.listMyBookings();

        // then
        assertEquals(1, responses.size());
        assertBookingEqualsResponse(booking, responses.get(0));
        verify(bookingRepository).findAllByArtistId(artist.getId());
    }

    @Test
    public void listMyBooking_shouldInvokeGetCustomerBookings_whenAuthorizedUserIsCustomer() {
        // given
        Artist artist = createDummyArtist();
        Customer customer = createDummyCustomer();
        Booking booking = createDummyBooking(artist, customer);

        when(userService.findAuthorizedUser()).thenReturn(customer.getUser());
        when(bookingRepository.findAllByCustomerId(customer.getId())).thenReturn(Collections.singletonList(booking));

        // when
        List<BookingResponse> responses = service.listMyBookings();

        // then
        assertEquals(1, responses.size());
        assertBookingEqualsResponse(booking, responses.get(0));
        verify(bookingRepository).findAllByCustomerId(customer.getId());
    }

    @Test
    public void addOffersToBooking_shouldInvokeOfferingServiceAndChangeStatus() {
        // given
        Booking booking = createDummyBooking(BookingStatus.NEW);
        List<LocalTime> offeredTimes = Collections.singletonList(LocalTime.now());

        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));

        // when
        service.addOffersToBooking(booking.getId(), offeredTimes);

        // then
        verify(offeringService).createOfferings(booking, offeredTimes);

        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        verify(bookingRepository).save(captor.capture());
        assertEquals(BookingStatus.OFFERED, captor.getValue().getStatus());
    }

    @Test(expected = BadRequestException.class)
    public void addOffersToBooking_shouldThrowBadRequestException_whenStatusNotNew() {
        // given
        Booking booking = createDummyBooking(BookingStatus.OFFERED);
        List<LocalTime> offeredTimes = Collections.singletonList(LocalTime.now());

        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));

        // when
        service.addOffersToBooking(booking.getId(), offeredTimes);
    }

    @Test(expected = NotFoundException.class)
    public void addOffersToBooking_shouldThrowNotFoundException_whenBookingNotExist() {
        // given
        Booking booking = createDummyBooking(BookingStatus.NEW);
        List<LocalTime> offeredTimes = Collections.singletonList(LocalTime.now());

        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.empty());

        // when
        service.addOffersToBooking(booking.getId(), offeredTimes);
    }

    private Answer<Booking> saveBookingAnswer() {
        return invocation -> (Booking) invocation.getArguments()[0];
    }
}