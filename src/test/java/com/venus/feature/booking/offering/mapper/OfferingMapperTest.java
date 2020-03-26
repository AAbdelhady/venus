package com.venus.feature.booking.offering.mapper;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.booking.offering.dto.OfferingResponse;
import com.venus.feature.booking.offering.entity.Offering;

import static com.venus.testutils.AssertionUtils.assertOfferingEqualsResponse;
import static com.venus.testutils.MapperTestUtils.offeringMapper;
import static com.venus.testutils.UnitTestUtils.createDummyBooking;
import static com.venus.testutils.UnitTestUtils.createDummyOffering;
import static org.junit.Assert.assertEquals;

public class OfferingMapperTest {

    @Test
    public void mapList_shouldMapToResponseCorrectly() {
        // given
        Booking booking = createDummyBooking();
        Offering offering = createDummyOffering(booking);

        // when
        List<OfferingResponse> responses = offeringMapper.mapList(Collections.singletonList(offering));

        // then
        assertEquals(1, responses.size());
        assertOfferingEqualsResponse(offering, responses.get(0));
    }
}