package com.venus.feature.artist.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.common.dto.response.PageResponse;

import static com.venus.testutils.AssertionUtils.assertArtistEqualsResponse;
import static com.venus.testutils.MapperTestUtils.artistMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void mapOne_shouldReturnNull_whenNullProvided() {
        // given - when
        ArtistResponse response = artistMapper.mapOne(null);

        // then
        assertNull(response);
    }

    @Test
    public void mapList_shouldMapListToResponse() {
        // given
        List<Artist> list = new ArrayList<>();
        list.add(createDummyArtist());
        list.add(createDummyArtist());

        // when
        List<ArtistResponse> response = artistMapper.mapList(list);
        List<ArtistResponse> emptyResponse = artistMapper.mapList(Collections.emptyList());

        // then
        assertEquals(list.size(), response.size());
        for (int i = 0; i < list.size(); i++)
            assertArtistEqualsResponse(list.get(i), response.get(i));

        assertTrue(emptyResponse.isEmpty());
    }

    @Test
    public void mapPage_shouldMapPageToResponse() {
        // given
        final long total = 100L;

        final Pageable pageable = PageRequest.of(10, 2);

        List<Artist> list = new ArrayList<>();
        list.add(createDummyArtist());
        list.add(createDummyArtist());

        Page<Artist> page = new PageImpl<>(list, pageable, total);

        // when
        PageResponse<ArtistResponse> response = artistMapper.mapPage(page);

        // then
        assertEquals(list.size(), response.getContent().size());
        assertEquals(total, response.getTotalElements());
        for (int i = 0; i < list.size(); i++)
            assertArtistEqualsResponse(list.get(i), response.getContent().get(i));
    }
}