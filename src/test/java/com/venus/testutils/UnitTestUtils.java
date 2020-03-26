package com.venus.testutils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.test.util.ReflectionTestUtils;

import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.booking.core.entity.BookingStatus;
import com.venus.feature.booking.offering.entity.Offering;
import com.venus.feature.common.enums.Role;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.specialty.entity.Speciality;
import com.venus.feature.user.entity.User;

import static com.venus.testutils.DateUtils.dateToOffsetDateTime;
import static com.venus.testutils.DateUtils.nowPlusDays;
import static com.venus.testutils.RandomUtils.random;
import static com.venus.testutils.RandomUtils.randomAlphabeticString;
import static com.venus.testutils.RandomUtils.randomBookingStatus;
import static com.venus.testutils.RandomUtils.randomId;
import static com.venus.testutils.RandomUtils.randomLong;
import static com.venus.testutils.RandomUtils.randomName;

public class UnitTestUtils {

    public static User createDummyUser(Long id, String firstName, String lastName, String email, Role role) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRole(role);
        return user;
    }

    public static User createDummyUser() {
        return createDummyUser(Role.UNSPECIFIED);
    }

    public static User createDummyUser(Role role) {
        final long id = randomId();
        final String firstName = "firstName-" + id;
        final String lastName = "lastName-" + id;
        final String email = "email-" + id + "@email.com";
        return createDummyUser(randomId(), firstName, lastName, email, role);
    }

    public static Artist createDummyArtist() {
        User artistUser = createDummyUser(Role.ARTIST);
        Artist artist = new Artist();
        ReflectionTestUtils.setField(artist, "id", artistUser.getId());
        artist.setUser(artistUser);
        artist.setActive(true);
        return artist;
    }

    public static Customer createDummyCustomer() {
        User customerUser = createDummyUser(Role.CUSTOMER);
        Customer customer = new Customer();
        ReflectionTestUtils.setField(customer, "id", customerUser.getId());
        customer.setUser(customerUser);
        return customer;
    }

    public static Booking createDummyBooking() {
        return createDummyBooking(createDummyArtist(), createDummyCustomer(), randomBookingStatus());
    }

    public static Booking createDummyBooking(BookingStatus status) {
        return createDummyBooking(createDummyArtist(), createDummyCustomer(), status);
    }

    public static Booking createDummyBooking(Artist artist, Customer customer) {
        return createDummyBooking(artist, customer, randomBookingStatus());
    }

    public static Booking createDummyBooking(Artist artist, Customer customer, BookingStatus status) {
        Booking booking = new Booking();
        booking.setId(randomId());
        booking.setArtist(artist);
        booking.setCustomer(customer);
        booking.setSpeciality(createDummySpeciality(artist));
        booking.setMessage(randomAlphabeticString(20));
        booking.setStatus(status);
        return booking;
    }

    public static Appointment createDummyAppointment() {
        return createDummyAppointment(createDummyArtist(), createDummyCustomer());
    }

    public static Appointment createDummyAppointment(Artist artist, Customer customer) {
        OffsetDateTime appointmentTime = dateToOffsetDateTime(nowPlusDays(random(1, 10)), ZoneOffset.UTC);
        Appointment appointment = new Appointment();
        appointment.setId(randomId());
        appointment.setArtist(artist);
        appointment.setCustomer(customer);
        appointment.setAppointmentTime(appointmentTime);
        return appointment;
    }

    public static Speciality createDummySpeciality(Artist artist) {
        Speciality speciality = new Speciality();
        speciality.setId(randomId());
        speciality.setArtist(artist);
        speciality.setName(randomName());
        speciality.setPrice(BigDecimal.valueOf(randomLong(10, 50)));
        return speciality;
    }

    public static Offering createDummyOffering(Booking booking) {
        Offering offering = new Offering();
        offering.setId(randomId());
        offering.setBooking(booking);
        offering.setTime(LocalTime.now());
        offering.setCreated(Instant.now());
        return offering;
    }

    public static void delay() {
        try {
            java.lang.Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
