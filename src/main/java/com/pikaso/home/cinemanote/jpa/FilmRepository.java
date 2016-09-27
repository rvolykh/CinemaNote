package com.pikaso.home.cinemanote.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pikaso.home.cinemanote.entity.Film;

/**
 * Jpa repository for {@link Film} entity
 * @author pikaso
 */
public interface FilmRepository extends JpaRepository<Film, Long> {

}
