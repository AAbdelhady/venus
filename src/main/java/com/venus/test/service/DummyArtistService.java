package com.venus.test.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.entity.Category;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;

import lombok.RequiredArgsConstructor;

import static com.venus.test.DummyUtils.random;

@Service
@Transactional
@RequiredArgsConstructor
public class DummyArtistService {

    private final DummyUserService dummyUserService;
    private final ArtistRepository artistRepository;

    public List<Artist> insertDummyArtists(int count) {
        List<Artist> artistList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            artistList.add(insertDummyArtist());
        }
        return artistList;
    }

    public Artist insertDummyArtist() {
        User user = dummyUserService.insertDummyUser(Role.ARTIST);
        Artist artist = new Artist();
        artist.setUser(user);
        artist.setCategory(randomCategory());
        return artistRepository.save(artist);
    }

    private Category randomCategory() {
        return Category.values()[random(0, Category.values().length - 1)];
    }
}
