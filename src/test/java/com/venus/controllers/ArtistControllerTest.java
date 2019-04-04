package com.venus.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import com.venus.domain.dtos.Artist.ArtistResponse;
import com.venus.services.artist.ArtistService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void findArtistById() throws Exception {
        /* given */
        ArtistResponse response = new ArtistResponse();
        response.setId(1L);
        response.setFirstName("first-name");
        response.setLastName("last-name");

        given(artistService.findArtistById(1L)).willReturn(response);

        /* when */
        String url = ArtistController.URL + "/1";
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.first_name").value(response.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(response.getLastName()))
        ;
    }
}