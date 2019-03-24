package com.venus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venus.domain.entities.user.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
