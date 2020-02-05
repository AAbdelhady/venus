package com.venus.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.venus.feature.booking.dto.BookingRequest;
import com.venus.feature.booking.dto.BookingResponse;
import com.venus.feature.booking.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createBooking(@RequestBody @Valid BookingRequest request) {
        return bookingService.createBooking(request);
    }
}
