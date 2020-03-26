package com.venus.feature.booking.core.repository;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.venus.IntegrationTest;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.customer.entity.Customer;

import static org.junit.Assert.assertEquals;

public class BookingRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void findAllByArtistId_shouldReturnCorrectBookings_whenArtistIdProvided() {
        // given
        Artist artistA = createArtist();
        Artist artistB = createArtist();

        Customer customerA = createCustomer();
        Customer customerB = createCustomer();

        Booking bookingAA = createBooking(artistA, customerA);
        Booking bookingAB = createBooking(artistA, customerB);
        Booking bookingBA = createBooking(artistB, customerA);

        // when
        List<Booking> artistA_bookings = bookingRepository.findAllByArtistId(artistA.getId());
        List<Booking> artistB_bookings = bookingRepository.findAllByArtistId(artistB.getId());

        // then
        assertEquals(2, artistA_bookings.size());
        assertEquals(bookingAA.getId(), artistA_bookings.get(0).getId());
        assertEquals(bookingAB.getId(), artistA_bookings.get(1).getId());

        assertEquals(1, artistB_bookings.size());
        assertEquals(bookingBA.getId(), artistB_bookings.get(0).getId());
    }

    @Test
    public void findAllByCustomerId_shouldReturnCorrectBookings_whenCustomerIdProvided() {
        // given
        Artist artistA = createArtist();
        Artist artistB = createArtist();

        Customer customerA = createCustomer();
        Customer customerB = createCustomer();

        Booking bookingAA = createBooking(artistA, customerA);
        Booking bookingAB = createBooking(artistA, customerB);
        Booking bookingBA = createBooking(artistB, customerA);

        // when
        List<Booking> customerA_bookings = bookingRepository.findAllByCustomerId(customerA.getId());
        List<Booking> customerB_bookings = bookingRepository.findAllByCustomerId(customerB.getId());

        // then
        assertEquals(2, customerA_bookings.size());
        assertEquals(bookingAA.getId(), customerA_bookings.get(0).getId());
        assertEquals(bookingBA.getId(), customerA_bookings.get(1).getId());

        assertEquals(1, customerB_bookings.size());
        assertEquals(bookingAB.getId(), customerB_bookings.get(0).getId());
    }
}