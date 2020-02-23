package com.venus.feature.specialty.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.specialty.dto.SpecialityRequest;
import com.venus.feature.specialty.dto.SpecialityResponse;
import com.venus.feature.specialty.entity.Speciality;
import com.venus.feature.specialty.mapper.SpecialityMapper;
import com.venus.feature.specialty.repository.SpecialityRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SpecialityService {

    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;

    public List<SpecialityResponse> addSpecialitiesToArtist(List<SpecialityRequest> requestList, Artist artist) {
        List<Speciality> specialities = requestList.stream().map(req -> prepareSpeciality(req, artist)).collect(Collectors.toList());
        specialities = specialityRepository.saveAll(specialities);
        return specialityMapper.mapList(specialities);
    }

    private Speciality prepareSpeciality(SpecialityRequest request, Artist artist) {
        Speciality speciality = new Speciality();
        speciality.setName(request.getName());
        speciality.setPrice(request.getPrice());
        speciality.setArtist(artist);
        return speciality;
    }
}
