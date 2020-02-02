package com.venus.feature.appointment.mapper;

import java.time.OffsetDateTime;

import org.junit.Test;

import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.customer.entity.Customer;

import static com.venus.testutils.MapperTestUtils.appointmentMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;
import static org.junit.Assert.assertEquals;

public class AppointmentMapperTest {

    @Test
    public void toDto_shouldMapToAppointmentResponse() {
        // given
        Artist artist = createDummyArtist(-1L);
        Customer customer = createDummyCustomer(-2L);
        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setArtist(artist);
        appointment.setCustomer(customer);
        appointment.setAppointmentTime(OffsetDateTime.now());

        // when
        AppointmentResponse response = appointmentMapper.mapOne(appointment);

        // then
        assertEquals(appointment.getId(), response.getId());
        assertEquals(appointment.getAppointmentTime(), response.getAppointmentTime());
        assertEquals(appointment.getArtist().getId(), response.getArtist().getUser().getId());
        assertEquals(appointment.getCustomer().getId(), response.getCustomer().getUser().getId());
    }
}