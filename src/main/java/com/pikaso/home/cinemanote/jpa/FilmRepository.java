package com.pikaso.home.cinemanote.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.pikaso.home.cinemanote.entity.Film;

/**
 * Jpa repository for {@link Film} entity
 * @author pikaso
 */
public interface FilmRepository extends Repository<Film, Long> {
	
	Optional<Film> findOne(Long id);
	
	Film save(Film film);
	
	List<Film>  findAll();
	
	void delete(Film film);
	
	boolean exists(Long id);
}
