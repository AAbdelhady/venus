package com.venus.feature.artist.mapper;

import org.mapstruct.Mapper;

import com.venus.config.GlobalMapperConfig;
import com.venus.feature.artist.dto.ArtistRequest;
import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.user.mapper.UserMapper;

@Mapper(config = GlobalMapperConfig.class, uses = UserMapper.class)
public interface ArtistMapper {

    ArtistResponse toDto(Artist entity);

    Artist toArtist(ArtistRequest artistRequest);
}
