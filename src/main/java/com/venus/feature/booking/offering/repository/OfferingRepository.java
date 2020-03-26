package com.venus.feature.booking.offering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venus.feature.booking.offering.entity.Offering;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, Long> {
}
