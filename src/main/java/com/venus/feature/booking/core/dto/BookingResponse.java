package com.venus.feature.booking.core.dto;

import java.time.LocalDate;
import java.util.List;

import com.venus.feature.artist.dto.response.ArtistResponse;
import com.venus.feature.booking.core.entity.BookingStatus;
import com.venus.feature.booking.offering.dto.OfferingResponse;
import com.venus.feature.customer.dto.CustomerResponse;
import com.venus.feature.specialty.dto.SpecialityResponse;

import lombok.Data;

@Data
public class BookingResponse {
    private Long id;
    private CustomerResponse customer;
    private ArtistResponse artist;
    private SpecialityResponse speciality;
    private LocalDate bookingDate;
    private String message;
    private BookingStatus status;
    private List<OfferingResponse> offerings;
}
