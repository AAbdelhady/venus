package com.venus.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.venus.feature.common.dto.response.PageResponse;
import com.venus.feature.notification.dto.NotificationResponse;
import com.venus.feature.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public PageResponse<NotificationResponse> getMyNotifications(Pageable pageable) {
        return notificationService.getMyNotifications(pageable);
    }
}
