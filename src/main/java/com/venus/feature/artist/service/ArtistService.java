package com.venus.feature.artist.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.exceptions.NotFoundException;
import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.mapper.ArtistMapper;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistService {

    private final UserService userService;
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    public ArtistResponse createArtist() {
        User user = userService.updateAuthorizedUserRole(Role.ARTIST);
        Artist artist = new Artist();
        artist.setUser(user);
        artist = artistRepository.save(artist);
        return artistMapper.mapOne(artist);
    }

    public ArtistResponse findArtistById(Long id) {
        Artist artist = artistRepository.findByUserId(id).orElseThrow(NotFoundException::new);
        return artistMapper.mapOne(artist);
    }
}
