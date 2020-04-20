package com.venus.feature.notification.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.venus.IntegrationTest;
import com.venus.feature.common.enums.Role;
import com.venus.feature.notification.entity.Notification;
import com.venus.feature.notification.entity.NotificationType;
import com.venus.feature.user.entity.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotificationRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    public void findByReceiverId_shouldReturnCorrectNotifications() {
        // given
        User customer = createUser(Role.CUSTOMER);
        User artist = createUser(Role.ARTIST);
        Notification notificationA = createNotification(customer, artist, NotificationType.BOOKING_OFFERING);
        Notification notificationB = createNotification(customer, artist, NotificationType.BOOKING_OFFERING);

        Pageable pageable = PageRequest.of(0, 20);

        // when
        Page<Notification> customerNotificationsPage = notificationRepository.findByReceiverIdOrderByCreatedDesc(customer.getId(), pageable);
        Page<Notification> artistNotificationsPage = notificationRepository.findByReceiverIdOrderByCreatedDesc(artist.getId(), pageable);

        // then
        assertFalse(customerNotificationsPage.isEmpty());
        assertEquals(notificationB, customerNotificationsPage.getContent().get(0));
        assertEquals(notificationA, customerNotificationsPage.getContent().get(1));

        assertTrue(artistNotificationsPage.isEmpty());
    }
}