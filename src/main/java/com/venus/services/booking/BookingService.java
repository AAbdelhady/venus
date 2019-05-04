package com.venus.services.booking;

import com.venus.domain.dtos.booking.BookingRequest;
import com.venus.domain.dtos.booking.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(BookingRequest bookingRequest);
}
