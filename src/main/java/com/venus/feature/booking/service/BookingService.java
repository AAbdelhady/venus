package com.venus.feature.booking.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.exceptions.NotFoundException;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.booking.dto.BookingRequest;
import com.venus.feature.booking.dto.BookingResponse;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.booking.mapper.BookingMapper;
import com.venus.feature.booking.repository.BookingRepository;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.customer.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final ArtistRepository artistRepository;

    private final CustomerRepository customerRepository;

    private final BookingMapper bookingMapper;

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Artist artist = artistRepository.findById(bookingRequest.getArtistId()).orElseThrow(NotFoundException::new);
        Customer customer = customerRepository.findById(bookingRequest.getCustomerId()).orElseThrow(NotFoundException::new);
        Booking booking = Booking.builder().artist(artist).customer(customer).message(bookingRequest.getMessage()).build();
        return bookingMapper.mapOne(bookingRepository.save(booking));
    }
}
