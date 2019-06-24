package com.venus.services.artist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.domain.dtos.artist.ArtistRequest;
import com.venus.domain.dtos.artist.ArtistResponse;
import com.venus.domain.entities.user.Artist;
import com.venus.domain.entities.user.User;
import com.venus.domain.enums.Role;
import com.venus.domain.mappers.ArtistMapper;
import com.venus.repositories.ArtistRepository;
import com.venus.services.user.UserService;

@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {

    private final UserService userService;

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    @Autowired
    public ArtistServiceImpl(UserService userService, ArtistRepository artistRepository, ArtistMapper artistMapper) {
        this.userService = userService;
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    @Override
    public ArtistResponse createArtist(ArtistRequest artistRequest) {
        User user = userService.findAuthorizedUser();
        user.setRole(Role.ARTIST);
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
