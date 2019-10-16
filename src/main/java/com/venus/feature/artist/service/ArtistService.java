package com.venus.feature.artist.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.feature.artist.dto.ArtistRequest;
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

    public ArtistResponse createArtist(ArtistRequest artistRequest) {
        User user = userService.findAuthorizedUser();
        user.setRole(Role.ARTIST);
        Artist artist = artistMapper.toArtist(artistRequest);
        artist.setUser(user);
        artist = artistRepository.save(artist);
        return artistMapper.toDto(artist);
    }

    public ArtistResponse findArtistById(Long id) {
        Artist artist = artistRepository.findByUserId(id).orElseThrow(IllegalArgumentException::new);
        return artistMapper.toDto(artist);
    }
}
