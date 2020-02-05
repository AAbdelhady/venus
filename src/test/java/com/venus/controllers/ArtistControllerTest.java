package com.venus.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.service.ArtistService;

import static com.venus.testutils.MapperTestUtils.artistMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArtistControllerTest extends MvcTest {

    @InjectMocks
    ArtistController controller;

    @Mock
    ArtistService artistService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        init(controller);
    }

    @Test
    public void createArtist() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.createArtist()).willReturn(response);

        // when
        mockMvc.perform(post("/artist").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(artist.getId()))
                .andExpect(jsonPath("$.firstName").value(artist.getUser().getFirstName()))
                .andExpect(jsonPath("$.lastName").value(artist.getUser().getLastName()))
                .andExpect(jsonPath("$.role").value(artist.getUser().getRole().name()));
    }

    @Test
    public void findArtistById() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.findArtistById(1L)).willReturn(response);

        // when
        mockMvc.perform(get("/artist/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(artist.getId()))
                .andExpect(jsonPath("$.firstName").value(artist.getUser().getFirstName()))
                .andExpect(jsonPath("$.lastName").value(artist.getUser().getLastName()))
                .andExpect(jsonPath("$.role").value(artist.getUser().getRole().name()));
    }
}