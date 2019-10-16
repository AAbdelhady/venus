package com.venus.testutils;

import org.springframework.test.util.ReflectionTestUtils;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.user.entity.User;

public class UnitTestUtils {

    public static User createDummyUser(Long id, String firstName, String lastName, String email) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        return user;
    }

    public static User createDummyUser(Long id) {
        return createDummyUser(id, "", "", "");
    }

    public static Artist createDummyArtist(Long id) {
        User artistUser = createDummyUser(id);
        Artist artist = new Artist();
        ReflectionTestUtils.setField(artist, "id", id);
        artist.setUser(artistUser);
        artist.setActive(true);
        return artist;
    }

    public static Customer createDummyCustomer(Long id) {
        User customerUser = createDummyUser(id);
        Customer customer = new Customer();
        ReflectionTestUtils.setField(customer, "id", id);
        customer.setUser(customerUser);
        return customer;
    }

    public static Booking createDummyBooking(Long id, Artist artist, Customer customer) {
        Booking booking = new Booking();
        booking.setId(id);
        booking.setArtist(artist);
        booking.setCustomer(customer);
        return booking;
    }
}
