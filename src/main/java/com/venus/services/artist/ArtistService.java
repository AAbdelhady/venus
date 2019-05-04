package com.venus.services.artist;

import com.venus.domain.dtos.artist.ArtistRequest;
import com.venus.domain.dtos.artist.ArtistResponse;

public interface ArtistService {

    ArtistResponse createArtist(ArtistRequest artistRequest);

    ArtistResponse findArtistById(Long id);

}
