package com.venus.services.artist;

import com.venus.domain.dtos.Artist.ArtistRequest;
import com.venus.domain.dtos.Artist.ArtistResponse;

public interface ArtistService {

    ArtistResponse createArtist(ArtistRequest artistRequest);

    ArtistResponse findArtistById(Long id);

}
