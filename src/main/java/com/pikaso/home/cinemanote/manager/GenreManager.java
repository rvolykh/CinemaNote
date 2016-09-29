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

@Component
public class GenreManager {
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional
	public Genre create(Genre genre) throws CinemaNoteUpdateException {
		if(Objects.nonNull(genre.getId())){
			throw new CinemaNoteUpdateException("Cannot create object with manually set id");
		}
		
		return genreRepository.save(genre);
	}
	
	public Genre delete(Long genreId) throws CinemaNoteUpdateException {
		Genre genre = genreRepository.findOne(genreId);
		if(Objects.isNull(genre)){
			throw new CinemaNoteUpdateException("Cannot delete non existing genre " + genreId);
		}
		genreRepository.delete(genre);
		
		return genre;
	}
	
	@Transactional
	public Genre addLocalization(LocalizedGenre localizedGenre) throws CinemaNoteUpdateException {
		Genre genre = genreRepository.findOne(localizedGenre.getGenre().getId());
		if(Objects.isNull(genre)){
			throw new CinemaNoteUpdateException("Cannot save localized genre with non existing genre");
		}
		localizedGenre.setGenre(genre);
		genre.getNames().add(localizedGenre);
		
		return genreRepository.save(genre);
	}
	
	public Genre removeLocalization(Long genreId, String language) throws CinemaNoteUpdateException {
		Genre genre = genreRepository.getOne(genreId);
		if(Objects.isNull(genre)){
			throw new CinemaNoteUpdateException("Cannot save localized genre with non existing genre");
		}
		genre.getNames().removeIf(x->x.getLanguage().equals(language));
		
		return genreRepository.save(genre);
	}
	
	public List<Genre> findLocalized(String language){
		return genreRepository.findByNamesLanguage(language);
	}
	
	public List<Genre> findAll(){
		return genreRepository.findAll();
	}
}
