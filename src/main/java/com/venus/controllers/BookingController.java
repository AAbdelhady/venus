package com.venus.controllers;

import java.time.LocalTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.venus.feature.booking.core.dto.BookingRequest;
import com.venus.feature.booking.core.dto.BookingResponse;
import com.venus.feature.booking.core.service.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public List<BookingResponse> listMyBookings() {
        return bookingService.listMyBookings();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse createBooking(@RequestBody @Valid BookingRequest request) {
        return bookingService.createBooking(request);
    }

    @PostMapping("{bookingId}/offer")
    @ResponseStatus(HttpStatus.CREATED)
    public void offerAppointmentTimes(@PathVariable Long bookingId, @RequestBody @NotNull List<@NotNull LocalTime> offeredTimes) {
        bookingService.addOffersToBooking(bookingId, offeredTimes);
    }
}
