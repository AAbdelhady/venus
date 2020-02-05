package com.venus.feature.appointment.mapper;

import org.junit.Test;

import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.customer.entity.Customer;

import static com.venus.testutils.AssertionUtils.assertArtistEqualsResponse;
import static com.venus.testutils.AssertionUtils.assertCustomerEqualsResponse;
import static com.venus.testutils.MapperTestUtils.appointmentMapper;
import static com.venus.testutils.UnitTestUtils.createDummyAppointment;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;
import static org.junit.Assert.assertEquals;

public class AppointmentMapperTest {

    @Test
    public void toDto_shouldMapToAppointmentResponse() {
        // given
        Artist artist = createDummyArtist();
        Customer customer = createDummyCustomer();
        Appointment appointment = createDummyAppointment(artist, customer);

        // when
        AppointmentResponse response = appointmentMapper.mapOne(appointment);

        // then
        assertEquals(appointment.getId(), response.getId());
        assertEquals(appointment.getAppointmentTime(), response.getAppointmentTime());
        assertArtistEqualsResponse(appointment.getArtist(), response.getArtist());
        assertCustomerEqualsResponse(appointment.getCustomer(), response.getCustomer());
    }
}