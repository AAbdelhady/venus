package com.venus.services.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.domain.dtos.booking.BookingRequest;
import com.venus.domain.dtos.booking.BookingResponse;
import com.venus.domain.entities.Booking;
import com.venus.domain.entities.user.Artist;
import com.venus.domain.entities.user.Customer;
import com.venus.domain.mappers.BookingMapper;
import com.venus.repositories.ArtistRepository;
import com.venus.repositories.BookingRepository;
import com.venus.repositories.CustomerRepository;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final ArtistRepository artistRepository;

    private final CustomerRepository customerRepository;

    private final BookingMapper bookingMapper;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, ArtistRepository artistRepository, CustomerRepository customerRepository, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.artistRepository = artistRepository;
        this.customerRepository = customerRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Artist artist = artistRepository.findById(bookingRequest.getArtistId()).orElseThrow(IllegalArgumentException::new);
        Customer customer = customerRepository.findById(bookingRequest.getCustomerId()).orElseThrow(IllegalArgumentException::new);
        Booking booking = Booking.builder().artist(artist).customer(customer).message(bookingRequest.getMessage()).build();
        return bookingMapper.toDto(bookingRepository.save(booking));
    }
}
