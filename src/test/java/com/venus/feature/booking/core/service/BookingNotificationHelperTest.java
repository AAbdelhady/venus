package com.venus.feature.booking.core.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.booking.core.entity.BookingStatus;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.notification.entity.NotificationType;
import com.venus.feature.notification.service.NotificationService;

import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummyBooking;
import static com.venus.testutils.UnitTestUtils.createDummyCustomer;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class BookingNotificationHelperTest {

    private BookingNotificationHelper notificationHelper;

    @Mock
    private NotificationService notificationService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        notificationHelper = new BookingNotificationHelper(notificationService);
    }

    @Test
    public void addNewBookingNotification_shouldAddNotificationWithCorrectArguments() {
        // given
        Artist artist = createDummyArtist();
        Customer customer = createDummyCustomer();
        Booking booking = createDummyBooking(artist, customer, BookingStatus.NEW);

        // when
        notificationHelper.addNewBookingNotification(booking);

        // then
        verify(notificationService).addBookingNotification(eq(artist.getUser()), eq(customer.getUser()), anyString(), anyString(), eq(NotificationType.BOOKING_REQUEST), eq(booking));
    }

    @Test
    public void addNewOffersNotification_shouldAddNotificationWithCorrectArguments() {
        // given
        Artist artist = createDummyArtist();
        Customer customer = createDummyCustomer();
        Booking booking = createDummyBooking(artist, customer, BookingStatus.OFFERED);

        // when
        notificationHelper.addNewOffersNotification(booking);

        // then
        verify(notificationService).addBookingNotification(eq(customer.getUser()), eq(artist.getUser()), anyString(), anyString(), eq(NotificationType.BOOKING_OFFERING), eq(booking));
    }
}