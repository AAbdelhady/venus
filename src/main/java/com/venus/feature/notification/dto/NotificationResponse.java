package com.venus.feature.notification.dto;

import java.time.Instant;

import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.booking.core.dto.BookingResponse;
import com.venus.feature.notification.entity.NotificationType;
import com.venus.feature.user.dto.UserResponse;

import lombok.Data;

@Data
public class NotificationResponse {
    private Long id;
    private UserResponse receiver;
    private UserResponse sender;
    private String title;
    private String body;
    private NotificationType type;
    private Instant created;
    private BookingResponse booking;
    private AppointmentResponse appointment;
}
