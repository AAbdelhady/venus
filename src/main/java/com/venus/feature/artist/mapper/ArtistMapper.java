package com.venus.feature.artist.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.user.mapper.UserMapper;

@Mapper(config = GlobalMapperConfig.class, uses = UserMapper.class)
public interface ArtistMapper {

    ArtistResponse mapOne(Artist artist);

    List<ArtistResponse> mapList(List<Artist> artist);

    default Page<ArtistResponse> mapPage(Page<Artist> artistPage) {
        List<ArtistResponse> responseList = mapList(artistPage.getContent());
        return new PageImpl<>(responseList, artistPage.getPageable(), artistPage.getTotalElements());
    }
}
