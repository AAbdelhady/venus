package com.venus.repositories;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.venus.BaseIntegrationTest;
import com.venus.domain.entities.user.Artist;
import com.venus.domain.entities.user.User;
import com.venus.domain.enums.Role;

import static org.junit.Assert.assertEquals;

@DataJpaTest
public class ArtistRepositoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    public void findByUser_IdTest() {
        // given
        User user = createUser(Role.ARTIST);
        Artist expected = new Artist();
        expected.setUser(user);
        expected = artistRepository.save(expected);

        // when
        Artist actual = artistRepository.findByUser_Id(user.getId()).orElseThrow(RuntimeException::new);

        // then
        assertEquals(expected.getId(), actual.getId());
        assertEquals(user.getId(), actual.getUser().getId());
        assertEquals(user.getId(), actual.getId());
    }
}