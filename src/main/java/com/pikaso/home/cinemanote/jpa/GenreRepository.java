package com.pikaso.home.cinemanote.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.pikaso.home.cinemanote.entity.Genre;

/**
 * Jpa repository for {@link Genre} entity
 * @author pikaso
 */
public interface GenreRepository extends Repository<Genre, Long> {

	Optional<Genre> findOne(Long id);
	
	Genre save(Genre genre);
	
	List<Genre>  findAll();
	
	void delete(Genre genre);
	
	boolean exists(Long id);
}
