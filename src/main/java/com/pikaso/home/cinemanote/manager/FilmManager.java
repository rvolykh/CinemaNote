package com.pikaso.home.cinemanote.manager;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pikaso.home.cinemanote.entity.Film;
import com.pikaso.home.cinemanote.entity.Genre;
import com.pikaso.home.cinemanote.entity.LocalizedFilm;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.jpa.FilmRepository;
import com.pikaso.home.cinemanote.jpa.GenreRepository;
import com.pikaso.home.cinemanote.util.LanguageUtil;

@Component
public class FilmManager {

	@Autowired
	private FilmRepository filmRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional
	public Film create(Film film) throws CinemaNoteUpdateException {
		if(Objects.nonNull(film.getId()) && film.getId() > 0){
			throw new CinemaNoteUpdateException("Cannot save film with manually set id");
		}
		
		return filmRepository.save(film);
	}
	
	@Transactional
	public Film modify(long filmId, Film modifiedFilm) throws CinemaNoteUpdateException {
		Film film = Optional.ofNullable(filmRepository.findOne(filmId))
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot modify non existing film " + filmId));

		film.editFrom(modifiedFilm);
		
		return filmRepository.save(film);
	}
	
	public Film find(long filmId, String language) throws CinemaNoteSelectException {
		Film film = Optional.ofNullable(filmRepository.findOne(filmId))
				.orElseThrow(()->new CinemaNoteSelectException("Cannot find film with id " + filmId));
		film.localize(language);
		
		return film;
	}
	
	public List<Film> find(String language){
		List<Film> films = filmRepository.findAll();
		films.forEach(x->x.localize(language));
		
		return films;
	}
	
	@Transactional
	public Film addLocalization(long filmId, LocalizedFilm localizedFilm) throws CinemaNoteUpdateException {
		Film film = Optional.ofNullable(filmRepository.findOne(filmId))
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot add localization to non existing film " + filmId));

		localizedFilm.setFilmId(film.getId());
		film.getLocalizattion().put(localizedFilm.getLanguage(), localizedFilm);
		
		return filmRepository.save(film);
	}
	
	@Transactional
	public Film removeLocalization(long filmId, String language) throws CinemaNoteUpdateException {
		Film film = Optional.ofNullable(filmRepository.findOne(filmId))
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot remove localization to non existing film " + filmId));
		
		int size = film.getLocalizattion().size();
		film.getLocalizattion().remove(language);
		if(size == film.getLocalizattion().size()){
			throw new CinemaNoteUpdateException(String.format("Cannot remove non existing localization on %s "
					+ "language. Maybe someone already deleted it.", LanguageUtil.getLanguage(language)));
		}
		
		return filmRepository.save(film);
	}
	
	@Transactional
	public Film addGenre(long filmId, long genreId) throws CinemaNoteUpdateException {
		Film film = Optional.ofNullable(filmRepository.findOne(filmId))
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot add genre to non existing film " + filmId));
		
		Genre genre = Optional.ofNullable(genreRepository.findOne(genreId))
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot add to film the non existing genre " + genreId));
		
		film.getGenres().add(genre);
		
		return filmRepository.save(film);
	}
	
	@Transactional
	public Film removeGenre(long filmId, long genreId) throws CinemaNoteUpdateException {
		Film film = Optional.ofNullable(filmRepository.findOne(filmId))
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot add genre to non existing film " + filmId));
		
		Genre genre = Optional.ofNullable(genreRepository.findOne(filmId))
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot add to film the non existing genre " + genreId));
		
		film.getGenres().remove(genre);
		
		return filmRepository.save(film);
	}
}
