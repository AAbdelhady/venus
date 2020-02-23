package com.venus.feature.artist.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.venus.exceptions.NotFoundException;
import com.venus.feature.artist.dto.ArtistRequest;
import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.dto.CategoryResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.entity.Category;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.assets.AssetsHelper;
import com.venus.feature.common.dto.response.PageResponse;
import com.venus.feature.common.enums.Role;
import com.venus.feature.specialty.dto.SpecialityRequest;
import com.venus.feature.specialty.dto.SpecialityResponse;
import com.venus.feature.specialty.service.SpecialityService;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.service.UserService;

import static com.venus.feature.localization.LocalizationConstants.BUNDLE_NAMES;
import static com.venus.feature.localization.LocalizationConstants.DEFAULT_LANG;
import static com.venus.feature.localization.LocalizationHelper.getLocalizedValue;
import static com.venus.testutils.AssertionUtils.assertArtistEqualsResponse;
import static com.venus.testutils.AssertionUtils.assertUserEqualsResponse;
import static com.venus.testutils.MapperTestUtils.artistMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummyUser;
import static com.venus.testutils.UnitTestUtils.randomId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ArtistServiceTest {

    private ArtistService service;

    @Mock
    private UserService userService;
    @Mock
    private SpecialityService specialityService;
    @Mock
    private ArtistRepository artistRepository;
    @Mock
    private AssetsHelper assetsHelper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ArtistService(userService, specialityService, artistRepository, artistMapper, assetsHelper);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void createArtist_shouldCreateArtistForUser() {
        // given
        User user = createDummyUser();
        when(userService.updateAuthorizedUserRole(Role.ARTIST)).thenReturn(user);
        when(artistRepository.save(any(Artist.class))).then(artistSaveAnswer());

        when(specialityService.addSpecialitiesToArtist(anyList(), any(Artist.class))).then(addSpecialitiesToArtistAnswer());

        SpecialityRequest specialityRequest = new SpecialityRequest();
        specialityRequest.setName("speciality name");
        specialityRequest.setPrice(BigDecimal.valueOf(10L));

        ArtistRequest request = new ArtistRequest();
        request.setCategory(Category.MAKE_UP);
        request.setSpecialities(Collections.singletonList(specialityRequest));

        // when
        ArtistResponse response = service.createArtist(request);

        // then
        assertUserEqualsResponse(user, response.getUser());
        assertEquals(request.getCategory(), response.getCategory());

        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<Artist> artistCaptor = ArgumentCaptor.forClass(Artist.class);
        verify(specialityService).addSpecialitiesToArtist(listCaptor.capture(), artistCaptor.capture());

        List<SpecialityRequest> actualSpecialities = listCaptor.getValue();
        assertEquals(1, actualSpecialities.size());
        assertEquals(specialityRequest.getName(), actualSpecialities.get(0).getName());
        assertEquals(specialityRequest.getPrice(), actualSpecialities.get(0).getPrice());
    }

    @Test
    public void findArtistById_shouldReturnArtist() {
        // given
        Artist artist = createDummyArtist();
        when(artistRepository.findByUserId(artist.getId())).thenReturn(Optional.of(artist));

        // when
        ArtistResponse response = service.findArtistById(artist.getId());

        // then
        assertArtistEqualsResponse(artist, response);
    }

    @Test(expected = NotFoundException.class)
    public void findArtistById_shouldThrowNotFoundException_whenUserWithIdNotFound() {
        // given
        final long id = -1;
        when(artistRepository.findByUserId(id)).thenReturn(Optional.empty());

        // when
        service.findArtistById(id);
    }

    @Test
    public void searchArtists_shouldReturnPage_whenPageableProvided() {
        // given
        final long total = 100L;

        final Pageable pageable = PageRequest.of(10, 2);

        List<Artist> list = new ArrayList<>();
        list.add(createDummyArtist());
        list.add(createDummyArtist());

        Page<Artist> page = new PageImpl<>(list, pageable, total);
        when(artistRepository.findAll(pageable)).thenReturn(page);

        // when
        PageResponse<ArtistResponse> response = service.searchArtists(pageable);

        // then
        assertEquals(list.size(), response.getContent().size());
        assertEquals(total, response.getTotalElements());
        for (int i = 0; i < list.size(); i++)
            assertArtistEqualsResponse(list.get(i), response.getContent().get(i));
    }

    @Test
    public void listCategories_shouldReturnSetOfCategories() {
        // when
        final String lang = DEFAULT_LANG;
        List<CategoryResponse> categories = service.listCategories(lang);

        //then
        assertEquals(Category.values().length, categories.size());
        Arrays.asList(Category.values()).forEach(c -> assertTrue(categories.stream().anyMatch(res -> res.getValue() == c)));
        Arrays.asList(Category.values()).forEach(c -> assertTrue(categories.stream().anyMatch(res -> res.getText().equals(getLocalizedValue(lang, BUNDLE_NAMES.CATEGORY, c.name())))));
    }

    private Answer<Artist> artistSaveAnswer() {
        return invocation -> invocation.getArgument(0);
    }

    private Answer<List<SpecialityResponse>> addSpecialitiesToArtistAnswer() {
        return invocation -> {
            List<SpecialityRequest> specialities = invocation.getArgument(0);
            return specialities.stream().map(s -> {
                SpecialityResponse response = new SpecialityResponse();
                response.setId(randomId());
                response.setName(s.getName());
                response.setPrice(s.getPrice());
                return response;
            }).collect(Collectors.toList());
        };
    }
}