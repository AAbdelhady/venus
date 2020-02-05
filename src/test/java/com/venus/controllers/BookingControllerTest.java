package com.venus.controllers;

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

import static com.venus.testutils.MapperTestUtils.bookingMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummyBooking;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

        BookingRequest request = new BookingRequest();
        request.setArtistId(artist.getId());
        request.setCustomerId(customer.getId());
        request.setMessage("message");

        Booking booking = createDummyBooking(artist, customer);
        BookingResponse response = bookingMapper.mapOne(booking);

        given(bookingService.createBooking(any(BookingRequest.class))).willReturn(response);

        // when
        mockMvc.perform(post("/booking").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(booking.getId()))
                .andExpect(jsonPath("$.artist.id").value(booking.getArtist().getId()))
                .andExpect(jsonPath("$.customer.id").value(booking.getCustomer().getId()))
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
}