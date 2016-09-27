package com.pikaso.home.cinemanote.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pikaso.home.cinemanote.entity.LocalizedGenre;

/**
 * Jpa repository for {@link LocalizedGenre} entity
 * @author pikaso
 */
public interface LocalizedGenreRepository extends JpaRepository<LocalizedGenre, Long> {

}
