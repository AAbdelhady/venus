package com.venus.feature.booking.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venus.feature.booking.core.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByArtistId(Long artistId);

    List<Booking> findAllByCustomerId(Long customerId);
}
