package com.venus.feature.artist.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.artist.dto.response.ArtistProfileResponse;
import com.venus.feature.artist.dto.response.ArtistResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.common.dto.response.PageResponse;
import com.venus.feature.user.mapper.UserMapper;
import com.venus.util.MapperUtils;

@Mapper(config = GlobalMapperConfig.class, uses = UserMapper.class)
public interface ArtistMapper {
    ArtistProfileResponse mapFull(Artist artist);
    ArtistResponse mapOne(Artist artist);
    List<ArtistResponse> mapList(List<Artist> artistList);
    default PageResponse<ArtistResponse> mapPage(Page<Artist> artistPage) {
        return MapperUtils.mapPage(artistPage, this::mapList);
    }
}
