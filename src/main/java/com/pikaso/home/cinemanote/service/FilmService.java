package com.pikaso.home.cinemanote.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pikaso.home.cinemanote.entity.Film;
import com.pikaso.home.cinemanote.entity.LocalizedFilm;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.exception.NotFoundException;
import com.pikaso.home.cinemanote.manager.FilmManager;
import com.pikaso.home.cinemanote.manager.Validator;
import com.pikaso.home.cinemanote.util.LanguageUtil;
import com.pikaso.home.cinemanote.view.FilmCreateDTO;
import com.pikaso.home.cinemanote.view.FilmInfoDTO;
import com.pikaso.home.cinemanote.view.FilmUpdateDTO;
import com.pikaso.home.cinemanote.view.LocalizedFilmUpdateDTO;

@RestController
@Api(name = "Film service", description = "Manage films information")
@ApiErrors(apierrors = {@ApiError(code="400", description="Request has malformed syntax")})
@RequestMapping(value = "/film", produces = MediaType.APPLICATION_JSON_VALUE)
public class FilmService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private FilmManager filmManager;
	
	@ApiMethod(description="Add new film's information")
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<FilmInfoDTO> create(@ApiBodyObject @RequestBody FilmCreateDTO filmDTO) {

		Film film = filmManager.create(filmDTO);
		
		return ResponseEntity.ok().body(film.toInfoDTO()); 
	}

	@ApiMethod(description="Modify the film's information")
	@RequestMapping(value="/{filmId}", method = RequestMethod.PUT)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Film not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<FilmInfoDTO> modify(@ApiPathParam(name="filmId", description="the film id") 
	@PathVariable Long filmId, @ApiBodyObject @RequestBody FilmUpdateDTO filmDTO) {

		try {
			Film film = filmManager.modify(filmId, filmDTO);
			return ResponseEntity.ok().body(film.toInfoDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	@ApiMethod(description="Find film by id")
	@RequestMapping(value="/{filmId}", method=RequestMethod.GET)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Film not found"),
			@ApiError(code="405", description="Language not in ISO639-1 format")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<FilmInfoDTO> findByIdAndLanguage(@ApiPathParam(name="filmId", description="the film id") 
			@PathVariable Long filmId, @ApiQueryParam(name="language", description="preferred language") 
			@RequestParam(required = false) String language) {
		
		if (StringUtils.isEmpty(language)) {
			language = LanguageUtil.getDefaultCode(); // TODO: read from Security user language
		} else {
			validator.verifyLanguage(language);
		}

		try {
			Film film = filmManager.find(filmId, language);
			return ResponseEntity.ok().body(film.toInfoDTO()); 
		} catch (CinemaNoteSelectException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	@ApiMethod(description="Find films")
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ApiErrors(apierrors = {@ApiError(code="405", description="Language in ISO639-1 format")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<List<FilmInfoDTO>> findAll(@ApiQueryParam(name="language", description="language to use if possible") 
	@RequestParam(required = false) String language) {
		
		if (StringUtils.isEmpty(language)) {
			language = LanguageUtil.getDefaultCode(); // TODO: read from Security user language
		} else {
			validator.verifyLanguage(language);
		}
		
		List<Film> films = filmManager.find(language);

		return ResponseEntity.ok().body(films.stream().map(Film::toInfoDTO).collect(toList())); 
	}
	
	@ApiMethod(description="Add the film's localization")
	@RequestMapping(value="/{filmId}/localization", method = RequestMethod.POST)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Film not found"), 
			@ApiError(code="405", description="Language field not in ISO639-1 format")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<FilmInfoDTO> addLocalization(@ApiPathParam(name="filmId", description="the film id") 
			@PathVariable Long filmId, @ApiBodyObject @RequestBody LocalizedFilmUpdateDTO localizationDTO) {
		
		validator.verifyLanguage(localizationDTO.getLanguage());
		
		try {
			Film film = filmManager.addLocalization(filmId, LocalizedFilm.from(localizationDTO));
			return ResponseEntity.ok().body(film.toInfoDTO());
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
	@ApiMethod(description="Remove the film's localization")
	@RequestMapping(value="/{filmId}/localization", method = RequestMethod.DELETE)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Film or localization not found"), 
			@ApiError(code="405", description="Language not in ISO639-1 format")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<FilmInfoDTO> removeLocalization(@ApiPathParam(name="filmId", description="the film id") 
			@PathVariable Long filmId, @ApiQueryParam(name = "language", description = "the language in ISO639-1")
			@RequestParam(required = true) String language) {
		
		validator.verifyLanguage(language);

		try {
			Film film = filmManager.removeLocalization(filmId, language);
			return ResponseEntity.ok().body(film.toInfoDTO());
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
	@ApiMethod(description="Add genre to film")
	@RequestMapping(value="/{filmId}/genre/{genreId}", method = RequestMethod.POST)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Film or genre not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<FilmInfoDTO> addGenre(@ApiPathParam(name="filmId", description="the film id") 
			@PathVariable Long filmId, @ApiPathParam(name="genreId", description="the genre id") 
			@PathVariable Long genreId) {
		
		try {
			Film film = filmManager.addGenre(filmId, genreId);
			return ResponseEntity.ok().body(film.toInfoDTO());
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
	@ApiMethod(description="Remove genre from film")
	@RequestMapping(value="/{filmId}/genre/{genreId}", method = RequestMethod.DELETE)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Film or genre not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<FilmInfoDTO> removeGenre(@ApiPathParam(name="filmId", description="the film id") 
			@PathVariable Long filmId, @ApiPathParam(name="genreId", description="the genre id") 
			@PathVariable Long genreId) {
		
		try {
			Film film = filmManager.removeGenre(filmId, genreId);
			return ResponseEntity.ok().body(film.toInfoDTO());
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
}
