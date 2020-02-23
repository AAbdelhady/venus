package com.venus.feature.specialty.mapper;

import org.junit.Before;
import org.junit.Test;

import com.venus.feature.specialty.dto.SpecialityResponse;
import com.venus.feature.specialty.entity.Speciality;

import static com.venus.testutils.AssertionUtils.assertSpecialityEqualsResponse;
import static com.venus.testutils.MapperTestUtils.specialityMapper;
import static com.venus.testutils.UnitTestUtils.createDummyArtist;
import static com.venus.testutils.UnitTestUtils.createDummySpeciality;

public class SpecialityMapperTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void mapOne_shouldMapCorrectly() {
        // given
        Speciality speciality = createDummySpeciality(createDummyArtist());

        // when
        SpecialityResponse response = specialityMapper.mapOne(speciality);

        // then
        assertSpecialityEqualsResponse(speciality, response);
    }
}