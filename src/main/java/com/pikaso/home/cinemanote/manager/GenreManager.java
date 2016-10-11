package com.pikaso.home.cinemanote.manager;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pikaso.home.cinemanote.entity.Genre;
import com.pikaso.home.cinemanote.entity.LocalizedGenre;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.jpa.GenreRepository;
import com.pikaso.home.cinemanote.util.LanguageUtil;

@Component
public class GenreManager {
	private static final Logger log = LoggerFactory.getLogger(GenreManager.class);
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional
	public Genre create(Genre genre) throws CinemaNoteUpdateException {
		if(Objects.nonNull(genre.getId()) && genre.getId() > 0){
			throw new CinemaNoteUpdateException("Cannot create object with manually set id");
		}
		
		log.info("User {} added new genre {}", "CinemaNote", genre.getName()); // TODO: read from Security
		
		return genreRepository.save(genre);
	}
	
	@Transactional
	public Genre modify(Long genreId, String newName) throws CinemaNoteUpdateException {
		Genre genre = genreRepository.findOne(genreId)
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot modify non existing genre " + genreId));
		
		log.info("User {} change genre {} to {}", "CinemaNote", genre.getName(), newName);
		
		genre.editFrom(newName);
		
		return genreRepository.save(genre);
	}
	
	@Transactional
	public Genre delete(Long genreId) throws CinemaNoteUpdateException {
		Genre genre = genreRepository.findOne(genreId)
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot delete non existing genre " + genreId));

		genreRepository.delete(genre);
		
		log.info("User {} deleted genre {}", "CinemaNote", genre.getName());
		
		return genre;
	}
	
	@Transactional
	public Genre addLocalization(Long genreId, LocalizedGenre localizedGenre) throws CinemaNoteUpdateException {
		Genre genre = genreRepository.findOne(genreId)
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot save localization for non existing genre " + genreId));

		localizedGenre.setGenreId(genre.getId());
		genre.getNames().put(localizedGenre.getLanguage(), localizedGenre);
		
		log.info("User {} add localization {} to genre {}", "CinemaNote", LanguageUtil.getLanguage(localizedGenre.getLanguage()), genre.getName()); // TODO: read from Security
		
		return genreRepository.save(genre);
	}
	
	@Transactional
	public Genre removeLocalization(Long genreId, String language) throws CinemaNoteUpdateException {
		Genre genre = genreRepository.findOne(genreId)
				.orElseThrow(()->new CinemaNoteUpdateException("Cannot remove localization for non existing genre" + genreId));

		int size = genre.getNames().size();
		genre.getNames().remove(language);
		if(size == genre.getNames().size()){
			throw new CinemaNoteUpdateException(String.format("Cannot remove non existing localization on %s "
					+ "language. Maybe someone already deleted it.", LanguageUtil.getLanguage(language)));
		}
		
		log.info("User {} removed localization {} for genre {}", "CinemaNote", LanguageUtil.getLanguage(language), genre.getName()); // TODO: read from Security
		
		return genreRepository.save(genre);
	}
	
	public List<Genre> findLocalized(String language) {
		List<Genre> genres = genreRepository.findAll();
		genres.forEach(x->x.localize(language));
		
		return genres;
	}
}
