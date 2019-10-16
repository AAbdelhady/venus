package com.venus.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;

import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.service.ArtistService;
import com.venus.feature.user.dto.UserResponse;

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
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);
        userResponse.setFirstName("first-name");
        userResponse.setLastName("last-name");
        ArtistResponse response = new ArtistResponse();
        response.setUser(userResponse);

        given(artistService.findArtistById(1L)).willReturn(response);

        /* when */
        mockMvc.perform(get("/artist/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userResponse.getId()))
                .andExpect(jsonPath("$.first_name").value(userResponse.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(userResponse.getLastName()));
    }
}