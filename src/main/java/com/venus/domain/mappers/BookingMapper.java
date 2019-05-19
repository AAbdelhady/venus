package com.venus.domain.mappers;

import org.mapstruct.Mapper;

import com.venus.domain.dtos.booking.BookingRequest;
import com.venus.domain.dtos.booking.BookingResponse;
import com.venus.domain.entities.Booking;

@Mapper(componentModel = "spring", uses = {ArtistMapper.class, CustomerMapper.class})
public interface BookingMapper {

    BookingResponse toDto(Booking entity);

    Booking toEntity(BookingRequest artistRequest);
}
