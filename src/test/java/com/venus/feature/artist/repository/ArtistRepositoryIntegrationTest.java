package com.venus.feature.artist.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.venus.BaseIntegrationTest;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;

import static org.junit.Assert.assertEquals;

@DataJpaTest
public class ArtistRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    public void findByUser_shouldReturnCorrectArtist() {
        // given
        User user = createUser(Role.ARTIST);
        Artist expected = new Artist();
        expected.setUser(user);
        expected = artistRepository.save(expected);

        // when
        Artist actual = artistRepository.findByUserId(user.getId()).orElseThrow(RuntimeException::new);

        // then
        assertEquals(expected.getId(), actual.getId());
        assertEquals(user.getId(), actual.getUser().getId());
        assertEquals(user.getId(), actual.getId());
    }
}