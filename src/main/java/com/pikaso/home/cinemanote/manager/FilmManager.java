package com.pikaso.home.cinemanote.manager;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Sets;
import com.pikaso.home.cinemanote.entity.Film;
import com.pikaso.home.cinemanote.entity.Genre;
import com.pikaso.home.cinemanote.entity.LocalizedFilm;
import com.pikaso.home.cinemanote.entity.User;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.jpa.FilmRepository;
import com.pikaso.home.cinemanote.jpa.GenreRepository;
import com.pikaso.home.cinemanote.util.LanguageUtil;
import com.pikaso.home.cinemanote.view.FilmCreateDTO;
import com.pikaso.home.cinemanote.view.FilmUpdateDTO;

@Component
public class FilmManager {
	private static final Logger log = LoggerFactory.getLogger(FilmManager.class);

	@Autowired
	private FilmRepository filmRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional
	public Film create(FilmCreateDTO filmDTO) {
		Set<Genre> genres = Optional.ofNullable(filmDTO.getGenreIds())
			.map(ids -> Sets.newHashSet(genreRepository.findAll(ids))).orElse(Sets.newHashSet());
		
		User user = null; // TODO: read from security
		
		// TODO: LOG not all genres found
		Film film = Film.from(filmDTO, genres, user);
		
		log.info("Film: {} was added by {}", film.getTitle(), "CinameNote");// TODO: active user name
		
		return filmRepository.save(film);
	}
	
	@Transactional
	public Film modify(long filmId, FilmUpdateDTO filmDTO) throws CinemaNoteUpdateException {
		Film film = filmRepository.findOne(filmId)
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot modify non existing film " + filmId));
		
		Set<Genre> genres = Optional.ofNullable(filmDTO.getGenreIds())
				.map(ids -> Sets.newHashSet(genreRepository.findAll(ids))).orElse(null);
		
		film.editFrom(filmDTO, genres);
		
		log.info("Film: {} was edited by {}", film.getTitle(), "CinameNote");// TODO: active user name
		
		return filmRepository.save(film);
	}
	
	public Film find(long filmId, String language) throws CinemaNoteSelectException {
		Film film = findOriginal(filmId);
		film.localize(language);
		
		return film;
	}
	
	public Film findOriginal(long filmId) throws CinemaNoteSelectException {
		Film film = filmRepository.findOne(filmId)
				.orElseThrow(()->new CinemaNoteSelectException("Cannot find film with id " + filmId));
		
		return film;
	}
	
	public List<Film> find(String language){
		List<Film> films = filmRepository.findAll();
		films.forEach(x->x.localize(language));
		
		return films;
	}
	
	@Transactional
	public Film addLocalization(long filmId, LocalizedFilm localizedFilm) throws CinemaNoteUpdateException {
		Film film = filmRepository.findOne(filmId)
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot add localization to non existing film " + filmId));

		localizedFilm.setFilmId(film.getId());
		film.getLocalizattion().put(localizedFilm.getLanguage(), localizedFilm);
		
		log.info("User {} add localization {} to film {}", "CinameNote", LanguageUtil.getLanguage(localizedFilm.getLanguage()), film.getTitle());// TODO: active user name
		
		return filmRepository.save(film);
	}
	
	@Transactional
	public Film removeLocalization(long filmId, String language) throws CinemaNoteUpdateException {
		Film film = filmRepository.findOne(filmId)
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot remove localization to non existing film " + filmId));
		
		int size = film.getLocalizattion().size();
		film.getLocalizattion().remove(language);
		if(size == film.getLocalizattion().size()){
			throw new CinemaNoteUpdateException(String.format("Cannot remove non existing localization on %s "
					+ "language. Maybe someone already deleted it.", LanguageUtil.getLanguage(language)));
		}
		
		log.info("User {} removed localization {} of film {}", "CinameNote", LanguageUtil.getLanguage(language), film.getTitle());// TODO: active user name
		
		return filmRepository.save(film);
	}
	
	@Transactional
	public Film addGenre(long filmId, long genreId) throws CinemaNoteUpdateException {
		Film film = filmRepository.findOne(filmId)
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot add genre to non existing film " + filmId));
		
		Genre genre = genreRepository.findOne(genreId)
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot add to film the non existing genre " + genreId));
		
		film.getGenres().add(genre);
		
		log.info("User {} added genre {} to film {}", "CinameNote", genre.getName(), film.getTitle());// TODO: active user name
		
		return filmRepository.save(film);
	}
	
	@Transactional
	public Film removeGenre(long filmId, long genreId) throws CinemaNoteUpdateException {
		Film film = filmRepository.findOne(filmId)
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot remove genre from non existing film " + filmId));
		
		Genre genre = genreRepository.findOne(genreId)
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot remove non existing genre " + genreId + " from film"));
		
		film.getGenres().remove(genre);
		
		log.info("User {} removed genre {} to film {}", "CinameNote", genre.getName(), film.getTitle());// TODO: active user name
		
		return filmRepository.save(film);
	}
}
