package com.venus.services.artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.domain.dtos.Artist.ArtistRequest;
import com.venus.domain.dtos.Artist.ArtistResponse;
import com.venus.domain.entities.user.Artist;
import com.venus.domain.entities.user.User;
import com.venus.domain.mappers.ArtistMapper;
import com.venus.repositories.ArtistRepository;
import com.venus.repositories.UserRepository;

@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    private final UserRepository userRepository;

    private final ArtistMapper artistMapper;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository, UserRepository userRepository, ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.artistMapper = artistMapper;
    }

    @Override
    public ArtistResponse createArtist(ArtistRequest artistRequest) {
        User user = userRepository.findAll().get(0); // find logged in
        Artist artist = artistMapper.toArtist(artistRequest);
        artist.setUser(user);
        artist = artistRepository.save(artist);
        return artistMapper.toDto(artist);
    }

    @Override
    public ArtistResponse findArtistById(Long id) {
        Artist artist = artistRepository.findByUser_Id(id).orElseThrow(IllegalArgumentException::new);
        return artistMapper.toDto(artist);
    }
}
