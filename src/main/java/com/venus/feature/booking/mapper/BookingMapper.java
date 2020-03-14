package com.venus.feature.booking.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.artist.mapper.ArtistMapper;
import com.venus.feature.booking.dto.BookingResponse;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.customer.mapper.CustomerMapper;
import com.venus.feature.specialty.mapper.SpecialityMapper;

@Mapper(config = GlobalMapperConfig.class, uses = {ArtistMapper.class, CustomerMapper.class, SpecialityMapper.class})
public interface BookingMapper {
    BookingResponse mapOne(Booking entity);

    List<BookingResponse> mapList(List<Booking> entity);
}
