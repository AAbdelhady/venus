package com.venus.feature.booking.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.exceptions.NotFoundException;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.booking.dto.BookingRequest;
import com.venus.feature.booking.dto.BookingResponse;
import com.venus.feature.booking.entity.Booking;
import com.venus.feature.booking.entity.BookingStatus;
import com.venus.feature.booking.mapper.BookingMapper;
import com.venus.feature.booking.repository.BookingRepository;
import com.venus.feature.common.enums.Role;
import com.venus.feature.customer.entity.Customer;
import com.venus.feature.customer.repository.CustomerRepository;
import com.venus.feature.specialty.entity.Speciality;
import com.venus.feature.specialty.repository.SpecialityRepository;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingService {

    private final UserService userService;
    private final BookingRepository bookingRepository;
    private final ArtistRepository artistRepository;
    private final CustomerRepository customerRepository;
    private final SpecialityRepository specialityRepository;
    private final BookingMapper bookingMapper;

    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Artist artist = artistRepository.findById(bookingRequest.getArtistId()).orElseThrow(NotFoundException::new);
        Customer customer = customerRepository.findById(bookingRequest.getCustomerId()).orElseThrow(NotFoundException::new);
        Speciality speciality = specialityRepository.findById(bookingRequest.getSpecialityId()).orElseThrow(NotFoundException::new);
        Booking booking = Booking.builder()
                .artist(artist)
                .customer(customer)
                .speciality(speciality)
                .message(bookingRequest.getMessage())
                .bookingDate(bookingRequest.getBookingDate())
                .status(BookingStatus.NEW)
                .build();
        return bookingMapper.mapOne(bookingRepository.save(booking));
    }

    public List<BookingResponse> listMyBookings() {
        User user = userService.findAuthorizedUser();
        List<Booking> bookings = user.getRole() == Role.ARTIST ? bookingRepository.findAllByArtistId(user.getId()) : bookingRepository.findAllByCustomerId(user.getId());
        return bookingMapper.mapList(bookings);
    }
}
