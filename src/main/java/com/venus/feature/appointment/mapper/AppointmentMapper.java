package com.venus.feature.appointment.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.appointment.dto.AppointmentResponse;
import com.venus.feature.appointment.entity.Appointment;
import com.venus.feature.artist.mapper.ArtistMapper;
import com.venus.feature.customer.mapper.CustomerMapper;

@Mapper(config = GlobalMapperConfig.class, uses = {ArtistMapper.class, CustomerMapper.class})
public interface AppointmentMapper {
    AppointmentResponse mapOne(Appointment entity);
    List<AppointmentResponse> mapList(List<Appointment> entities);
}
