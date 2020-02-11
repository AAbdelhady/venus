package com.venus.feature.artist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.venus.exceptions.NotFoundException;
import com.venus.feature.artist.dto.ArtistResponse;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.repository.ArtistRepository;
import com.venus.feature.common.enums.Role;
import com.venus.feature.user.entity.User;
import com.venus.feature.user.service.UserService;

import static com.venus.testutils.AssertionUtils.assertArtistEqualsResponse;
import static com.venus.testutils.AssertionUtils.assertUserEqualsResponse;
import static com.venus.testutils.MapperTestUtils.artistMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummyUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ArtistServiceTest {

    private ArtistService service;
    @Mock
    private UserService userService;
    @Mock
    private ArtistRepository artistRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ArtistService(userService, artistRepository, artistMapper);
    }

    @Test
    public void createArtist_shouldCreateArtistForUser() {
        // given
        User user = createDummyUser();
        when(userService.updateAuthorizedUserRole(Role.ARTIST)).thenReturn(user);
        when(artistRepository.save(any(Artist.class))).then(artistSaveAnswer());

        // when
        ArtistResponse response = service.createArtist();

        // then
        assertUserEqualsResponse(user, response.getUser());
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
        Page<ArtistResponse> response = service.searchArtists(pageable);

        // then
        assertEquals(list.size(), response.getContent().size());
        assertEquals(total, response.getTotalElements());
        assertEquals(pageable, response.getPageable());
        for (int i = 0; i < list.size(); i++)
            assertArtistEqualsResponse(list.get(i), response.getContent().get(i));
    }

    private Answer<Artist> artistSaveAnswer() {
        return invocation -> invocation.getArgument(0);
    }
}