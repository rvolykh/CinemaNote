package com.pikaso.home.cinemanote.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pikaso.home.cinemanote.entity.LocalizedFilm;

/**
 * Jpa repository for {@link LocalizedFilm} entity
 * @author pikaso
 */
public interface LocalizedFilmRepository extends JpaRepository<LocalizedFilm, Long> {

}
