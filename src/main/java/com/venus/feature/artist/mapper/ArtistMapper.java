package com.venus.feature.artist.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.common.dto.response.PageResponse;
import com.venus.feature.user.mapper.UserMapper;

@Mapper(config = GlobalMapperConfig.class, uses = UserMapper.class)
public interface ArtistMapper {

    ArtistResponse mapOne(Artist artist);

    List<ArtistResponse> mapList(List<Artist> artistList);

    default PageResponse<ArtistResponse> mapPage(Page<Artist> artistPage) {
        List<ArtistResponse> responseList = mapList(artistPage.getContent());
        return new PageResponse<>(responseList, artistPage.getTotalElements());
    }
}
