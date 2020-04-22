package com.venus.feature.notification.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.appointment.mapper.AppointmentMapper;
import com.venus.feature.booking.core.mapper.BookingMapper;
import com.venus.feature.common.dto.response.PageResponse;
import com.venus.feature.notification.dto.NotificationResponse;
import com.venus.feature.notification.entity.Notification;
import com.venus.feature.user.mapper.UserMapper;
import com.venus.util.MapperUtils;

@Mapper(config = GlobalMapperConfig.class, uses = {UserMapper.class, BookingMapper.class, AppointmentMapper.class})
public interface NotificationMapper {
    NotificationResponse mapOne(Notification notification);
    List<NotificationResponse> mapList(List<Notification> notificationList);
    default PageResponse<NotificationResponse> mapPage(Page<Notification> notificationPage) {
        return MapperUtils.mapPage(notificationPage, this::mapList);
    }
}
