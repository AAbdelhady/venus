package com.venus.feature.booking.offering.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.booking.offering.dto.OfferingResponse;
import com.venus.feature.booking.offering.entity.Offering;

@Mapper(config = GlobalMapperConfig.class)
public interface OfferingMapper {
    List<OfferingResponse> mapList(List<Offering> entity);
}
