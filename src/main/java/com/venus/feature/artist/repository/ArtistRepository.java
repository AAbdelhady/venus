package com.venus.feature.artist.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venus.feature.artist.entity.Artist;
import com.venus.feature.artist.entity.Category;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByUserId(long userId);

    Page<Artist> findByCategory(Category category, Pageable pageable);
}
