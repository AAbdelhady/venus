package com.venus.feature.specialty.repository;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.venus.DbTest;
import com.venus.feature.artist.entity.Artist;
import com.venus.feature.specialty.entity.Speciality;

import static org.junit.Assert.assertEquals;

public class SpecialityRepositoryIntegrationTest extends DbTest {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Test(expected = DataIntegrityViolationException.class)
    public void save_shouldThrowDataIntegrityViolationException_whenSpecialityWithDuplicatedArtistAndNameProvided() {
        // given
        final Artist artist = createArtist();
        final String name = "speciality name";

        // when
        createSpeciality(artist, name);
        createSpeciality(artist, name);

        // then
        List<Speciality> all = specialityRepository.findAll();
        assertEquals(2, all.size());
    }
}