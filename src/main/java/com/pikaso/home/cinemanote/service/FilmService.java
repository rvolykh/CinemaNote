package com.pikaso.home.cinemanote.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pikaso.home.cinemanote.entity.Film;
import com.pikaso.home.cinemanote.entity.LocalizedFilm;
import com.pikaso.home.cinemanote.exception.BadRequestException;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.exception.NotFoundException;
import com.pikaso.home.cinemanote.manager.FilmManager;
import com.pikaso.home.cinemanote.view.FilmInfoDTO;
import com.pikaso.home.cinemanote.view.FilmUpdateDTO;
import com.pikaso.home.cinemanote.view.LocalizedFilmUpdateDTO;

@RestController
@Api(description = "The film controller", name = "Film service")
@ApiErrors(apierrors = {@ApiError(code="405", description="Bad input parameters")})
@RequestMapping(value = "/film", produces = MediaType.APPLICATION_JSON_VALUE)
public class FilmService {

	@Autowired
	private FilmManager filmManager;
	
	/**
	 * Store film
	 * @param filmDTO film update DTO
	 * @return stored film
	 */
	@ApiMethod(description="Add new film")
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<FilmInfoDTO> create(@ApiBodyObject @RequestBody FilmUpdateDTO filmDTO) {
		Film film = Film.from(filmDTO);

		try {
			film = filmManager.create(film);
			return ResponseEntity.ok().body(film.toInfoDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	/**
	 * Modify film information
	 * @param filmId the film id to modify
	 * @param filmDTO film update DTO
	 * @return the updated film
	 */
	@ApiMethod(description="Modify film information")
	@RequestMapping(value="/{filmId}", method = RequestMethod.PUT)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Film with given id not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<FilmInfoDTO> modify(@ApiPathParam(name="filmId", description="the film id") 
	@PathVariable Long filmId, @ApiBodyObject @RequestBody FilmUpdateDTO filmDTO) {

		Film film = Film.from(filmDTO);

		try {
			film = filmManager.modify(filmId, film);
			return ResponseEntity.ok().body(film.toInfoDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	/**
	 * Find film by id
	 * @param filmId the user id to find
	 * @return found film
	 */
	@ApiMethod(description="Find film by id")
	@RequestMapping(value="/{filmId}", method=RequestMethod.GET)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Film with given id not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<FilmInfoDTO> findById(@ApiPathParam(name="filmId", description="the film id") @PathVariable Long filmId) {

		try {
			Film film = filmManager.find(filmId);
			return ResponseEntity.ok().body(film.toInfoDTO()); 
		} catch (CinemaNoteSelectException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	/**
	 * Find all films
	 * @return found films
	 */
	@ApiMethod(description="Find all films")
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<List<FilmInfoDTO>> findAll() {
		List<Film> films = filmManager.find();

		return ResponseEntity.ok().body(films.stream().map(Film::toInfoDTO).collect(toList())); 
	}
	
	/**
	 * Add localization to film
	 * @param localizationDTO film localization update DTO
	 * @return the film
	 */
	@ApiMethod(description="Add new film")
	@RequestMapping(value="/localization/{filmId}", method = RequestMethod.POST)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<FilmInfoDTO> addLocalization(@ApiPathParam(name="filmId", description="the film id") 
			@PathVariable Long filmId, @ApiBodyObject @RequestBody LocalizedFilmUpdateDTO localizationDTO) {
		LocalizedFilm localization = LocalizedFilm.from(localizationDTO);

		try {
			Film film = filmManager.addLocalization(filmId, localization);
			return ResponseEntity.ok().body(film.toInfoDTO());
		} catch (CinemaNoteUpdateException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
}
