package com.venus.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;

import com.venus.feature.artist.dto.ArtistRequest;
import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.dto.CategoryResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.entity.Category;
import com.venus.feature.artist.service.ArtistService;
import com.venus.feature.common.dto.response.PageResponse;
import com.venus.feature.specialty.dto.SpecialityRequest;

import static com.venus.feature.localization.LocalizationConstants.ACCEPT_LANGUAGE_HEADER;
import static com.venus.feature.localization.LocalizationConstants.DEFAULT_LANG;
import static com.venus.testutils.MapperTestUtils.artistMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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
    public void searchArtists_shouldInvokeArtistServiceWithPageable() throws Exception {
        // given
        final int pageNumber = 2;
        final int pageSize = 10;
        final String sortField = "id";
        final String sortDirection = "asc";
        final int total = 120;

        List<Artist> artistList = new ArrayList<>();
        for (int i = 0; i < pageSize; i++) {
            artistList.add(createDummyArtist());
        }
        List<ArtistResponse> responseList = artistMapper.mapList(artistList);
        PageResponse<ArtistResponse> responsePage = new PageResponse<>(responseList, total);
        given(artistService.searchArtists(any(Pageable.class))).willReturn(responsePage);

        // when
        String sortString = sortField + "," + sortDirection;
        mockMvc.perform(get("/artist")
                .param("page", String.valueOf(pageNumber))
                .param("size", String.valueOf(pageSize))
                .param("sort", sortString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(artistList.size())))
                .andExpect(jsonPath("$.totalElements").value(total));

        // then
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
        verify(artistService).searchArtists(captor.capture());
        assertEquals(pageSize, captor.getValue().getPageSize());
        assertEquals(pageNumber, captor.getValue().getPageNumber());
        assertEquals(pageSize, captor.getValue().getPageSize());
        assertEquals(sortField + ": " + sortDirection.toUpperCase(), captor.getValue().getSort().toString());
    }

    @Test
    public void createArtist_shouldReturnBadRequest_whenRequestBodyMissing() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.createArtist(any(ArtistRequest.class))).willReturn(response);

        // when
        mockMvc.perform(post("/artist").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createArtist_shouldReturnBadRequest_whenRequestBodyEmpty() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.createArtist(any(ArtistRequest.class))).willReturn(response);

        // when
        mockMvc.perform(post("/artist").content(objectMapper.writeValueAsString(new ArtistRequest())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createArtist_shouldReturnBadRequest_whenRequestBodyMissingSpecialities() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.createArtist(any(ArtistRequest.class))).willReturn(response);

        ArtistRequest request = new ArtistRequest();
        request.setCategory(Category.TATTOO);

        // when
        mockMvc.perform(post("/artist").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createArtist_shouldReturnBadRequest_whenRequestBodyWithEmptySpecialities() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.createArtist(any(ArtistRequest.class))).willReturn(response);

        ArtistRequest request = new ArtistRequest();
        request.setCategory(Category.TATTOO);
        request.setSpecialities(Collections.emptyList());

        // when
        mockMvc.perform(post("/artist").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createArtist_shouldReturnBadRequest_whenRequestBodyWithNullSpecialities() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.createArtist(any(ArtistRequest.class))).willReturn(response);

        ArtistRequest request = new ArtistRequest();
        request.setCategory(Category.TATTOO);
        request.setSpecialities(Collections.singletonList(null));

        // when
        mockMvc.perform(post("/artist").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createArtist_shouldReturnBadRequest_whenRequestBodyWithEmptySpeciality() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.createArtist(any(ArtistRequest.class))).willReturn(response);

        ArtistRequest request = new ArtistRequest();
        request.setCategory(Category.TATTOO);
        request.setSpecialities(Collections.singletonList(new SpecialityRequest()));

        // when
        mockMvc.perform(post("/artist").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createArtist_shouldReturnBadRequest_whenRequestBodyWithSpecialityMissingPrice() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.createArtist(any(ArtistRequest.class))).willReturn(response);

        SpecialityRequest specialityRequest = new SpecialityRequest();
        specialityRequest.setName("name");

        ArtistRequest request = new ArtistRequest();
        request.setCategory(Category.TATTOO);
        request.setSpecialities(Collections.singletonList(specialityRequest));

        // when
        mockMvc.perform(post("/artist").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createArtist_shouldReturnBadRequest_whenRequestBodyWithSpecialityMissingName() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.createArtist(any(ArtistRequest.class))).willReturn(response);

        SpecialityRequest specialityRequest = new SpecialityRequest();
        specialityRequest.setPrice(BigDecimal.TEN);

        ArtistRequest request = new ArtistRequest();
        request.setCategory(Category.TATTOO);
        request.setSpecialities(Collections.singletonList(specialityRequest));

        // when
        mockMvc.perform(post("/artist").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createArtist_shouldReturnBadRequest_whenRequestBodyMissingCategory() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.createArtist(any(ArtistRequest.class))).willReturn(response);

        SpecialityRequest specialityRequest = new SpecialityRequest();
        specialityRequest.setName("name");
        specialityRequest.setPrice(BigDecimal.TEN);

        ArtistRequest request = new ArtistRequest();
        request.setSpecialities(Collections.singletonList(specialityRequest));

        // when
        mockMvc.perform(post("/artist").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createArtist_shouldInvokeArtistServiceWithRequestDto_whenRequestBodyValid() throws Exception {
        // given
        Artist artist = createDummyArtist();
        ArtistResponse response = artistMapper.mapOne(artist);

        given(artistService.createArtist(any(ArtistRequest.class))).willReturn(response);

        SpecialityRequest specialityRequest = new SpecialityRequest();
        specialityRequest.setName("name");
        specialityRequest.setPrice(BigDecimal.TEN);

        ArtistRequest request = new ArtistRequest();
        request.setCategory(Category.TATTOO);
        request.setSpecialities(Collections.singletonList(specialityRequest));

        // when
        mockMvc.perform(post("/artist").content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(artist.getId()))
                .andExpect(jsonPath("$.firstName").value(artist.getUser().getFirstName()))
                .andExpect(jsonPath("$.lastName").value(artist.getUser().getLastName()))
                .andExpect(jsonPath("$.role").value(artist.getUser().getRole().name()));
    }

    @Test
    public void findArtistById_shouldInvokeArtistServiceWithId() throws Exception {
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

    @Test
    public void listCategories_shouldUseDefaultLanguage_whenNoLanguageHeaderProvided() throws Exception {
        // given
        CategoryResponse response = new CategoryResponse();
        response.setValue(Category.TATTOO);
        response.setText("Tattoo");
        List<CategoryResponse> categories = Collections.singletonList(response);

        given(artistService.listCategories(anyString())).willReturn(categories);

        // when
        mockMvc.perform(get("/artist/category").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(categories.size())))
                .andExpect(jsonPath("$[0].value").value(response.getValue().toString()))
                .andExpect(jsonPath("$[0].text").value(response.getText()))
                .andExpect(jsonPath("$[0].photoUrl").doesNotExist());

        // then
        verify(artistService).listCategories(DEFAULT_LANG);
    }

    @Test
    public void listCategories_shouldUseProvidedLanguage_whenLanguageHeaderProvided() throws Exception {
        // given
        final String lang = "de";

        CategoryResponse response = new CategoryResponse();
        response.setValue(Category.TATTOO);
        response.setText("Tattoo");
        List<CategoryResponse> categories = Collections.singletonList(response);

        given(artistService.listCategories(anyString())).willReturn(categories);

        // when
        mockMvc.perform(get("/artist/category").header(ACCEPT_LANGUAGE_HEADER, lang).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(categories.size())))
                .andExpect(jsonPath("$[0].value").value(response.getValue().toString()))
                .andExpect(jsonPath("$[0].text").value(response.getText()))
                .andExpect(jsonPath("$[0].photoUrl").doesNotExist());

        // then
        verify(artistService).listCategories(lang);
    }
}