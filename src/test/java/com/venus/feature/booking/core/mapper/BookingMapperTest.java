package com.venus.feature.booking.core.mapper;

import java.util.Collections;

import org.junit.Test;

import com.venus.feature.booking.core.dto.BookingResponse;
import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.booking.offering.entity.Offering;

import static com.venus.testutils.AssertionUtils.assertBookingEqualsResponse;
import static com.venus.testutils.MapperTestUtils.bookingMapper;
import static com.venus.testutils.UnitTestUtils.createDummyBooking;
import static com.venus.testutils.UnitTestUtils.createDummyOffering;

public class BookingMapperTest {

    @Test
    public void mapOne_shouldMapToResponse() {
        // given
        Booking booking = createDummyBooking();
        Offering offering = createDummyOffering(booking);
        booking.setOfferings(Collections.singletonList(offering));

        // when
        BookingResponse response = bookingMapper.mapOne(booking);

        // then
        assertBookingEqualsResponse(booking, response);
    }
}