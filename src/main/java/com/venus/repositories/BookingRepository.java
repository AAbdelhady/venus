package com.venus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venus.domain.entities.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
