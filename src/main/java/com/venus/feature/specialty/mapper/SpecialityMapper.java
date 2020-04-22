package com.venus.feature.specialty.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.specialty.dto.SpecialityResponse;
import com.venus.feature.specialty.entity.Speciality;

@Mapper(config = GlobalMapperConfig.class)
public interface SpecialityMapper {
    SpecialityResponse mapOne(Speciality speciality);
    List<SpecialityResponse> mapList(List<Speciality> specialityList);
}
