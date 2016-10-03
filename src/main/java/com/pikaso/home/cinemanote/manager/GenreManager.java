package com.pikaso.home.cinemanote.manager;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pikaso.home.cinemanote.entity.Genre;
import com.pikaso.home.cinemanote.entity.LocalizedGenre;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.jpa.GenreRepository;
import com.pikaso.home.cinemanote.util.LanguageUtil;

@Component
public class GenreManager {
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional
	public Genre create(Genre genre) throws CinemaNoteUpdateException {
		if(Objects.nonNull(genre.getId()) && genre.getId() > 0){
			throw new CinemaNoteUpdateException("Cannot create object with manually set id");
		}
		
		return genreRepository.save(genre);
	}
	
	@Transactional
	public Genre delete(Long genreId) throws CinemaNoteUpdateException {
		Genre genre = genreRepository.findOne(genreId);
		if(Objects.isNull(genre)){
			throw new CinemaNoteUpdateException("Cannot delete non existing genre " + genreId);
		}
		genreRepository.delete(genre);
		
		return genre;
	}
	
	@Transactional
	public Genre modify(Long genreId, Genre modifiedGenre) throws CinemaNoteUpdateException {
		Genre genre = genreRepository.findOne(genreId);
		if(Objects.isNull(genre)){
			throw new CinemaNoteUpdateException("Cannot modify non existing genre " + genreId);
		}
		genre.editFrom(modifiedGenre);
		
		return genreRepository.save(genre);
	}
	
	@Transactional
	public Genre addLocalization(Long genreId, LocalizedGenre localizedGenre) throws CinemaNoteUpdateException {
		Genre genre = genreRepository.findOne(genreId);
		if(Objects.isNull(genre)){
			throw new CinemaNoteUpdateException("Cannot save localization for non existing genre");
		}
		localizedGenre.setGenreId(genre.getId());
		genre.getNames().add(localizedGenre);
		
		return genreRepository.save(genre);
	}
	
	@Transactional
	public Genre removeLocalization(Long genreId, String language) throws CinemaNoteUpdateException {
		Genre genre = genreRepository.findOne(genreId);
		if(Objects.isNull(genre)){
			throw new CinemaNoteUpdateException("Cannot remove localization for non existing genre");
		}
		int size = genre.getNames().size();
		genre.getNames().removeIf(x->x.getLanguage().equals(language));
		if(size == genre.getNames().size()){
			throw new CinemaNoteUpdateException(String.format("Cannot remove non existing localization on %s "
					+ "language. Maybe someone already deleted it.", LanguageUtil.getLanguage(language)));
		}
		
		return genreRepository.save(genre);
	}
	
	public List<Genre> findLocalized(String language) {
		List<Genre> genres = genreRepository.findAll();
		genres.forEach(x->x.localize(language));
		
		return genres;
	}
}
