package com.venus.testutils;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.venus.feature.artist.dto.response.ArtistProfileResponse;
import com.venus.feature.artist.dto.response.ArtistResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.booking.dto.BookingResponse;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.customer.dto.CustomerResponse;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.specialty.dto.SpecialityResponse;
import com.venus.feature.specialty.entity.Speciality;
import com.venus.feature.user.dto.UserResponse;
import com.venus.feature.user.entity.User;

import static com.venus.testutils.DateUtils.dateMinusSeconds;
import static com.venus.testutils.DateUtils.datePlusSeconds;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssertionUtils {

    public static void assertNowApprox(Date date) {
        assertDateEqualsApprox(new Date(), date);
    }

    public static void assertDateEqualsApprox(Date expected, Date actual) {
        final int approxInSec = 60;
        Date expectedPlus = datePlusSeconds(expected, approxInSec);
        Date expectedMinus = dateMinusSeconds(expected, approxInSec);
        assertTrue(actual.before(expectedPlus) && actual.after(expectedMinus));
    }

    public static void assertArtistEqualsResponse(Artist artist, ArtistResponse response) {
        assertUserEqualsResponse(artist.getUser(), response.getUser());
        assertEquals(artist.getCategory(), response.getCategory());
    }

    public static void assertArtistEqualsResponse(Artist artist, ArtistProfileResponse response) {
        assertUserEqualsResponse(artist.getUser(), response.getUser());
        assertEquals(artist.getCategory(), response.getCategory());
        assertEquals(artist.getSpecialityList().size(), response.getSpecialityList().size());
        for (int i = 0; i < artist.getSpecialityList().size(); i++) {
            assertSpecialityEqualsResponse(artist.getSpecialityList().get(i), response.getSpecialityList().get(i));
        }
    }

    public static void assertCustomerEqualsResponse(Customer customer, CustomerResponse response) {
        assertUserEqualsResponse(customer.getUser(), response.getUser());
    }

    public static void assertUserEqualsResponse(User user, UserResponse response) {
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getFirstName(), response.getFirstName());
        assertEquals(user.getLastName(), response.getLastName());
        assertEquals(user.getRole(), response.getRole());
    }

    public static void assertSpecialityEqualsResponse(Speciality speciality, SpecialityResponse response) {
        assertEquals(speciality.getId(), response.getId());
        assertEquals(speciality.getName(), response.getName());
        assertEquals(speciality.getPrice(), response.getPrice());
    }

    public static void assertBookingEqualsResponse(Booking booking, BookingResponse response) {
        assertEquals(booking.getId(), response.getId());
        assertArtistEqualsResponse(booking.getArtist(), response.getArtist());
        assertCustomerEqualsResponse(booking.getCustomer(), response.getCustomer());
        assertSpecialityEqualsResponse(booking.getSpeciality(), response.getSpeciality());
        assertEquals(booking.getBookingDate(), response.getBookingDate());
        assertEquals(booking.getMessage(), response.getMessage());
        assertEquals(booking.getStatus(), response.getStatus());
    }

    public static void assertUserInSecurityContext(User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertEquals(user.getId().longValue(), Long.parseLong(authentication.getPrincipal().toString()));
        assertEquals(1, authentication.getAuthorities().size());
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().getAuthority());
        assertTrue(authentication.getAuthorities().contains(grantedAuthority));
    }
}
