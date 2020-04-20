package com.venus.feature.booking.core.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.notification.entity.NotificationType;
import com.venus.feature.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Component
@Transactional
@RequiredArgsConstructor
class BookingNotificationHelper {

    private final NotificationService notificationService;

    void addNewBookingNotification(Booking booking) {
        notificationService.addBookingNotification(
                booking.getArtist().getUser(),
                booking.getCustomer().getUser(),
                "New booking request",
                String.format("%s sent you a booking request", booking.getCustomer().getUser().getFullName()),
                NotificationType.BOOKING_REQUEST,
                booking
        );
    }

    void addNewOffersNotification(Booking booking) {
        notificationService.addBookingNotification(
                booking.getCustomer().getUser(),
                booking.getArtist().getUser(),
                "Times offered",
                String.format("%s has offered you times for booking you requested", booking.getArtist().getUser().getFullName()),
                NotificationType.BOOKING_OFFERING,
                booking
        );
    }
}
