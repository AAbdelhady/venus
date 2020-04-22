package com.venus.feature.appointment.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.notification.entity.NotificationType;
import com.venus.feature.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@Component
@Transactional
@RequiredArgsConstructor
class AppointmentNotificationHelper {

    private final NotificationService notificationService;

    void addArtistAppointmentConfirmedNotification(Appointment appointment) {
        notificationService.addAppointmentNotification(
                appointment.getArtist().getUser(),
                appointment.getCustomer().getUser(),
                "Appointment Confirmed!",
                String.format("%s confirmed the appointment", appointment.getCustomer().getUser().getFullName()),
                NotificationType.APPOINTMENT_CONFIRMED,
                appointment
        );
    }

    void addCustomerAppointmentConfirmedNotification(Appointment appointment) {
        notificationService.addAppointmentNotification(
                appointment.getCustomer().getUser(),
                appointment.getArtist().getUser(),
                "Appointment Confirmed!",
                "Appointment confirmed :)",
                NotificationType.APPOINTMENT_CONFIRMED,
                appointment
        );
    }
}
