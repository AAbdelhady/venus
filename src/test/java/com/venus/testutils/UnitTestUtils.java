package com.venus.testutils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.UUID;

import org.springframework.test.util.ReflectionTestUtils;

import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.common.enums.Role;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.specialty.entity.Speciality;
import com.venus.feature.user.entity.User;

import static com.venus.testutils.DateUtils.dateToOffsetDateTime;
import static com.venus.testutils.DateUtils.nowPlusDays;

public class UnitTestUtils {

    private static final String ALPHABET_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String NUMERALS_STRING = "0123456789";

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
        User artistUser = createDummyUser();
        Artist artist = new Artist();
        ReflectionTestUtils.setField(artist, "id", artistUser.getId());
        artist.setUser(artistUser);
        artist.setActive(true);
        return artist;
    }

    public static Customer createDummyCustomer() {
        User customerUser = createDummyUser();
        Customer customer = new Customer();
        ReflectionTestUtils.setField(customer, "id", customerUser.getId());
        customer.setUser(customerUser);
        return customer;
    }

    public static Booking createDummyBooking(Artist artist, Customer customer) {
        Booking booking = new Booking();
        booking.setId(randomId());
        booking.setArtist(artist);
        booking.setCustomer(customer);
        return booking;
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
        speciality.setName(UUID.randomUUID().toString());
        speciality.setPrice(BigDecimal.valueOf(randomLong(10, 50)));
        return speciality;
    }

    public static String randomName() {
        return randomAlphabeticString(random(6, 16));
    }

    public static String randomEmail(String name) {
        String domain = randomAlphabeticString(6);
        return name + "@" + domain + "com";
    }

    public static String randomAlphabeticString(int length) {
        return randomString(length, ALPHABET_STRING);
    }

    public static String randomNumericString(int length) {
        return randomString(length, NUMERALS_STRING);
    }

    public static String randomString(int length, String source) {
        StringBuilder builder = new StringBuilder();
        while (length-- != 0) {
            int character = (int) (Math.random() * source.length());
            builder.append(source.charAt(character));
        }
        return builder.toString();
    }

    public static long randomId() {
        return randomLong(0, 10000);
    }

    public static long randomLong(int min, int max) {
        return Integer.valueOf(random(min, max)).longValue();
    }

    public static int random(int min, int max) {
        Random r = new Random();
        return r.ints(min, max).findFirst().orElseThrow(RuntimeException::new);
    }

    public static void delay() {
        try {
            java.lang.Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
