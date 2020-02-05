package com.venus.feature.artist.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.venus.DbTest;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;

import static org.junit.Assert.assertEquals;

public class ArtistRepositoryIntegrationTest extends DbTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    public void findByUser_shouldReturnCorrectArtist() {
        // given
        User user = createUser(Role.ARTIST);
        Artist expected = createArtist(user);

        // when
        Artist actual = artistRepository.findByUserId(user.getId()).orElseThrow(RuntimeException::new);

        // then
        assertEquals(expected.getId(), actual.getId());
        assertEquals(user.getId(), actual.getUser().getId());
        assertEquals(user.getId(), actual.getId());
    }
}