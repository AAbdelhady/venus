package com.venus.feature.artist.mapper;

import org.junit.Test;

import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.entity.Artist;

import static com.venus.testutils.AssertionUtils.assertArtistEqualsResponse;
import static com.venus.testutils.MapperTestUtils.artistMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static org.junit.Assert.assertNull;

public class ArtistMapperTest {

    @Test
    public void mapOne_shouldMapArtistToResponse_whenArtistProvided() {
        // given
        Artist artist = createDummyArtist();

        // when
        ArtistResponse response = artistMapper.mapOne(artist);

        // then
        assertArtistEqualsResponse(artist, response);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void mapOne_shouldReturnNull_whenNullProvided() {
        // given
        Artist artist = null;

        // when
        ArtistResponse response = artistMapper.mapOne(artist);

        // then
        assertNull(response);
    }
}