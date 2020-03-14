package com.venus.feature.specialty.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.specialty.dto.SpecialityRequest;
import com.venus.feature.specialty.dto.SpecialityResponse;
import com.venus.feature.specialty.entity.Speciality;
import com.venus.feature.specialty.repository.SpecialityRepository;

import static com.venus.testutils.MapperTestUtils.specialityMapper;
import static com.venus.testutils.RandomUtils.randomId;
import static com.venus.testutils.RandomUtils.randomLong;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

public class SpecialityServiceTest {

    private SpecialityService service;

    @Mock
    private SpecialityRepository specialityRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new SpecialityService(specialityRepository, specialityMapper);
    }

    @Test
    public void addSpecialitiesToArtist_shouldInvokeRepository() {
        // given
        Artist artist = createDummyArtist();

        when(specialityRepository.saveAll(ArgumentMatchers.anyCollection())).then(saveAllAnswer());

        List<SpecialityRequest> requestList = new ArrayList<>();
        requestList.add(prepareSpecialityRequest());
        requestList.add(prepareSpecialityRequest());

        // when
        List<SpecialityResponse> responseList = service.addSpecialitiesToArtist(requestList, artist);

        // then
        assertEquals(requestList.size(), responseList.size());
        assertEquals(requestList.get(0).getName(), responseList.get(0).getName());
        assertEquals(requestList.get(0).getPrice(), responseList.get(0).getPrice());
        assertEquals(requestList.get(1).getName(), responseList.get(1).getName());
        assertEquals(requestList.get(1).getPrice(), responseList.get(1).getPrice());
    }

    private SpecialityRequest prepareSpecialityRequest() {
        SpecialityRequest req = new SpecialityRequest();
        req.setName(UUID.randomUUID().toString());
        req.setPrice(BigDecimal.valueOf(randomLong(10, 99)));
        return req;
    }

    private Answer<List<Speciality>> saveAllAnswer() {
        return invocation -> {
            List<Speciality> specialities = invocation.getArgument(0);
            specialities.forEach(s -> s.setId(randomId()));
            return specialities;
        };
    }
}