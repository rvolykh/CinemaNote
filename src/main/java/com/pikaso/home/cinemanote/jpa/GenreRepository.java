package com.pikaso.home.cinemanote.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pikaso.home.cinemanote.entity.Genre;

/**
 * Jpa repository for {@link Genre} entity
 * @author pikaso
 */
public interface GenreRepository extends JpaRepository<Genre, Long> {

	List<Genre> findByNamesLanguage(String language);
	
}
