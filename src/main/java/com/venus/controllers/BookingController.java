package com.venus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.venus.domain.dtos.booking.BookingRequest;
import com.venus.domain.dtos.booking.BookingResponse;
import com.venus.services.booking.BookingService;

@RestController
@RequestMapping(BookingController.URL)
public class BookingController {

    static final String URL = "/api/booking";

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createArtist(@RequestBody BookingRequest request) {
        return bookingService.createBooking(request);
    }
}
