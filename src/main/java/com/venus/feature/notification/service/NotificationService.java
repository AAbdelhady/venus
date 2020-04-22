package com.venus.feature.notification.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.common.dto.response.PageResponse;
import com.venus.feature.notification.dto.NotificationResponse;
import com.venus.feature.notification.entity.Notification;
import com.venus.feature.notification.entity.NotificationType;
import com.venus.feature.notification.mapper.NotificationMapper;
import com.venus.feature.notification.repository.NotificationRepository;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final UserService userService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public void addAppointmentNotification(User receiver, User sender, String title, String body, NotificationType type, Appointment appointment) {
        addNotification(receiver, sender, title, body, type, null, appointment);
    }

    public void addBookingNotification(User receiver, User sender, String title, String body, NotificationType type, Booking booking) {
        addNotification(receiver, sender, title, body, type, booking, null);
    }

    private void addNotification(User receiver, User sender, String title, String body, NotificationType type, Booking booking, Appointment appointment) {
        Notification notification = Notification.builder()
                .receiver(receiver).sender(sender)
                .booking(booking).appointment(appointment)
                .title(title).body(body)
                .type(type).build();
        notificationRepository.save(notification);
    }

    public PageResponse<NotificationResponse> getMyNotifications(Pageable pageable) {
        User authorizedUser = userService.findAuthorizedUser();
        Page<Notification> page = notificationRepository.findByReceiverIdOrderByCreatedDesc(authorizedUser.getId(), pageable);
        return notificationMapper.mapPage(page);
    }
}
