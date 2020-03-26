package com.venus.feature.booking.offering.service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.feature.booking.core.entity.Booking;
import com.venus.feature.booking.offering.entity.Offering;
import com.venus.feature.booking.offering.repository.OfferingRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OfferingService {

    private final OfferingRepository offeringRepository;

    public void createOfferings(Booking booking, List<LocalTime> offeredTimes) {
        List<Offering> offerings = offeredTimes.stream().map(t -> new Offering(booking, t)).collect(Collectors.toList());
        offeringRepository.saveAll(offerings);
    }
}
