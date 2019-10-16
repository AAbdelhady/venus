package com.venus.feature.booking.mapper;

import org.mapstruct.Mapper;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.artist.mapper.ArtistMapper;
import com.venus.feature.booking.dto.BookingRequest;
import com.venus.feature.booking.dto.BookingResponse;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.customer.mapper.CustomerMapper;

@Mapper(config = GlobalMapperConfig.class, uses = {ArtistMapper.class, CustomerMapper.class})
public interface BookingMapper {

    BookingResponse toDto(Booking entity);

    Booking toEntity(BookingRequest artistRequest);
}
