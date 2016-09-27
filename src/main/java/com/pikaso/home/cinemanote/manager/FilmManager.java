package com.pikaso.home.cinemanote.manager;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pikaso.home.cinemanote.entity.Film;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.jpa.FilmRepository;

@Component
public class FilmManager {

	@Autowired
	private FilmRepository filmRepository;
	
	public Film create(Film film){
		return filmRepository.save(film);
	}
	
	public Film modify(long filmId, Film film) throws CinemaNoteUpdateException {
		if(!filmRepository.exists(filmId)){
			throw new CinemaNoteUpdateException("Cannot modify none existing user " + filmId);
		}
		film.setId(filmId);
		
		return filmRepository.save(film);
	}
	
	public Film find(long id) throws CinemaNoteSelectException {
		Optional<Film> film = Optional.ofNullable(filmRepository.findOne(id));
		if(film.isPresent()){
			return film.get();
		}
		
		throw new CinemaNoteSelectException("Cannot find film with id " + id);
	}
	
	public List<Film> find(){
		return filmRepository.findAll();
	}
}
