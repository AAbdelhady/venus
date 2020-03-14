package com.venus.feature.booking.mapper;

import org.junit.Test;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.booking.dto.BookingResponse;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.customer.entity.Customer;

import static com.venus.testutils.AssertionUtils.assertBookingEqualsResponse;
import static com.venus.testutils.MapperTestUtils.bookingMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummyBooking;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;

public class BookingMapperTest {

    @Test
    public void mapOne() {
        // given
        Artist artist = createDummyArtist();
        Customer customer = createDummyCustomer();
        Booking booking = createDummyBooking(artist, customer);

        // when
        BookingResponse response = bookingMapper.mapOne(booking);

        // then
        assertBookingEqualsResponse(booking, response);
    }
}