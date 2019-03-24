package com.venus.domain.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.venus.domain.dtos.Artist.ArtistRequest;
import com.venus.domain.dtos.Artist.ArtistResponse;
import com.venus.domain.entities.user.Artist;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    ArtistResponse toDto(Artist entity);

    Artist toArtist(ArtistRequest artistRequest);
}
