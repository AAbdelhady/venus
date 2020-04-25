package com.venus.feature.booking.core.service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.exceptions.BadRequestException;
import com.venus.exceptions.NotFoundException;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.booking.core.dto.BookingRequest;
import com.venus.feature.booking.core.dto.BookingResponse;
import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.booking.core.entity.BookingStatus;
import com.venus.feature.booking.core.mapper.BookingMapper;
import com.venus.feature.booking.core.repository.BookingRepository;
import com.venus.feature.booking.offering.entity.Offering;
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
    private final BookingNotificationHelper notificationHelper;

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
        booking = bookingRepository.save(booking);
        notificationHelper.addNewBookingNotification(booking);
        return bookingMapper.mapOne(booking);
    }

    public List<BookingResponse> listMyBookings() {
        User user = userService.findAuthorizedUser();
        return listBookingsByUser(user);
    }

    public List<BookingResponse> listBookingsByUser(User user) {
        List<Booking> bookings = user.getRole() == Role.ARTIST ? bookingRepository.findAllByArtistId(user.getId()) : bookingRepository.findAllByCustomerId(user.getId());
        return bookingMapper.mapList(bookings);
    }

    public void addOffersToBooking(Long bookingId, List<LocalTime> offeredTimes) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(NotFoundException::new);
        assertBookingNew(booking);
        booking.setStatus(BookingStatus.OFFERED);
        booking.getOfferings().addAll(prepareOfferings(booking, offeredTimes));
        booking = bookingRepository.save(booking);
        notificationHelper.addNewOffersNotification(booking);
    }

    private void assertBookingNew(Booking booking) {
        if (booking.getStatus() != BookingStatus.NEW) {
            throw new BadRequestException("Offers have already been made to this booking!");
        }
    }

    private List<Offering> prepareOfferings(Booking booking, List<LocalTime> offeredTimes) {
        return offeredTimes.stream().map(t -> new Offering(booking, t)).collect(Collectors.toList());
    }
}
