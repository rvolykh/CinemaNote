package com.pikaso.home.cinemanote.manager;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pikaso.home.cinemanote.entity.Film;
import com.pikaso.home.cinemanote.entity.LocalizedFilm;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.jpa.FilmRepository;

@Component
public class FilmManager {

	@Autowired
	private FilmRepository filmRepository;
	
	@Transactional
	public Film create(Film film) throws CinemaNoteUpdateException {
		if(Objects.nonNull(film.getId())){
			throw new CinemaNoteUpdateException("Cannot save film with manually set id");
		}
		
		return filmRepository.save(film);
	}
	
	@Transactional
	public Film modify(long filmId, Film film) throws CinemaNoteUpdateException {
		if(!filmRepository.exists(filmId)){
			throw new CinemaNoteUpdateException("Cannot modify none existing user " + filmId);
		}
		film.setId(filmId);
		
		return filmRepository.save(film);
	}
	
	public Film find(long filmId) throws CinemaNoteSelectException {
		Film film = filmRepository.findOne(filmId);
		if(Objects.isNull(film)){
			throw new CinemaNoteSelectException("Cannot find film with id " + filmId);
		}
		
		return film;
	}
	
	public List<Film> find(){
		return filmRepository.findAll();
	}
	
	@Transactional
	public Film addLocalization(long filmId, LocalizedFilm localizedFilm) throws CinemaNoteUpdateException {
		Film film = filmRepository.findOne(filmId);
		if(Objects.isNull(film)){
			throw new CinemaNoteUpdateException("Cannot add localization to non existing film " + filmId);
		}
		localizedFilm.setFilm(film);
		film.getLocalizattion().add(localizedFilm);
		
		return filmRepository.save(film);
	}
	
	@Transactional
	public Film removeLocalization(long filmId, String language) throws CinemaNoteUpdateException {
		Film film = filmRepository.findOne(filmId);
		if(Objects.isNull(film)){
			throw new CinemaNoteUpdateException("Cannot add localization to non existing film " + filmId);
		}
		film.getLocalizattion().removeIf(x->x.getLanguage().equals(language));
		
		return filmRepository.save(film);
	}
}
