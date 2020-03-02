package com.venus.feature.artist.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.venus.IntegrationTest;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.entity.Category;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArtistRepositoryIntegrationTest extends IntegrationTest {

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

    @Test
    public void findByCategory_shouldFindArtistsWithCategoryProvided() {
        // given
        Artist artistA = createArtist(Category.TATTOO);
        Artist artistB = createArtist(Category.TATTOO);
        Artist artistC = createArtist(Category.MAKE_UP);

        Pageable pageable = PageRequest.of(0, 20);

        // when
        Page<Artist> resultA = artistRepository.findByCategory(Category.TATTOO, pageable);
        Page<Artist> resultB = artistRepository.findByCategory(Category.MAKE_UP, pageable);
        Page<Artist> resultC = artistRepository.findByCategory(Category.PHOTOGRAPHY, pageable);

        // then
        assertEquals(2, resultA.getTotalElements());
        assertEquals(artistA.getId(), resultA.getContent().get(0).getId());
        assertEquals(artistB.getId(), resultA.getContent().get(1).getId());

        assertEquals(1, resultB.getTotalElements());
        assertEquals(artistC.getId(), resultB.getContent().get(0).getId());

        assertEquals(0, resultC.getTotalElements());
        assertTrue(resultC.getContent().isEmpty());
    }
}