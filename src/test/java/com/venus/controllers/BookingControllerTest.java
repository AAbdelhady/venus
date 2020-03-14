package com.venus.controllers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.booking.dto.BookingRequest;
import com.venus.feature.booking.dto.BookingResponse;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.booking.service.BookingService;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.specialty.entity.Speciality;

import static com.venus.testutils.MapperTestUtils.bookingMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummyBooking;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;
import static com.venus.testutils.UnitTestUtils.createDummySpeciality;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookingControllerTest extends MvcTest {

    @InjectMocks
    BookingController controller;

    @Mock
    BookingService bookingService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        init(controller);
    }

    @Test
    public void createBooking_shouldReturnCreated_whenRequestValid() throws Exception {
        // given
        Artist artist = createDummyArtist();
        Customer customer = createDummyCustomer();
        Speciality speciality = createDummySpeciality(artist);

        Map<String, String> request = new HashMap<>();
        request.put("artistId", artist.getId().toString());
        request.put("customerId", customer.getId().toString());
        request.put("specialityId", speciality.getId().toString());
        request.put("bookingDate", LocalDate.now().toString());
        request.put("message", "message");

        Booking booking = createDummyBooking(artist, customer);
        BookingResponse response = bookingMapper.mapOne(booking);

        given(bookingService.createBooking(any(BookingRequest.class))).willReturn(response);

        // when
        mockMvc.perform(post("/booking").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(booking.getId()))
                .andExpect(jsonPath("$.artist.id").value(booking.getArtist().getId()))
                .andExpect(jsonPath("$.customer.id").value(booking.getCustomer().getId()))
                .andExpect(jsonPath("$.speciality.id").value(booking.getSpeciality().getId()))
                .andExpect(jsonPath("$.bookingDate").value(booking.getBookingDate()))
                .andExpect(jsonPath("$.message").value(booking.getMessage()));
    }

    @Test
    public void createBooking_shouldReturnBadRequest_whenArtistIdIsMissing() throws Exception {
        // given
        BookingRequest request = new BookingRequest();
        request.setArtistId(null);
        request.setCustomerId(2L);
        request.setMessage("message");

        // when
        mockMvc.perform(post("/booking").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBooking_shouldReturnBadRequest_whenCustomerIdIsMissing() throws Exception {
        // given
        BookingRequest request = new BookingRequest();
        request.setArtistId(1L);
        request.setCustomerId(null);
        request.setMessage("message");

        // when
        mockMvc.perform(post("/booking").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBooking_shouldReturnBadRequest_whenSpecialityIdIsMissing() throws Exception {
        // given
        BookingRequest request = new BookingRequest();
        request.setArtistId(1L);
        request.setCustomerId(2L);
        request.setSpecialityId(null);
        request.setMessage("message");

        // when
        mockMvc.perform(post("/booking").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createBooking_shouldReturnBadRequest_whenBookingDateIsMissing() throws Exception {
        // given
        BookingRequest request = new BookingRequest();
        request.setArtistId(1L);
        request.setCustomerId(2L);
        request.setSpecialityId(3L);
        request.setBookingDate(null);
        request.setMessage("message");

        // when
        mockMvc.perform(post("/booking").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void listMyBookings_shouldReturnOk_whenRequestValid() throws Exception {
        // given
        Artist artist = createDummyArtist();
        Customer customer = createDummyCustomer();

        Booking booking = createDummyBooking(artist, customer);
        BookingResponse response = bookingMapper.mapOne(booking);

        given(bookingService.listMyBookings()).willReturn(Collections.singletonList(response));

        // when
        mockMvc.perform(get("/booking").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(booking.getId()))
                .andExpect(jsonPath("$[0].artist.id").value(booking.getArtist().getId()))
                .andExpect(jsonPath("$[0].customer.id").value(booking.getCustomer().getId()))
                .andExpect(jsonPath("$[0].message").value(booking.getMessage()));

        // then
        verify(bookingService).listMyBookings();
    }
}