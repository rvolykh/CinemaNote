package com.pikaso.home.cinemanote.service;

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

import com.pikaso.home.cinemanote.entity.Genre;
import com.pikaso.home.cinemanote.entity.LocalizedGenre;
import com.pikaso.home.cinemanote.exception.BadRequestException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.exception.NotFoundException;
import com.pikaso.home.cinemanote.manager.GenreManager;
import com.pikaso.home.cinemanote.util.LanguageUtil;
import com.pikaso.home.cinemanote.util.ValidatorUtil;
import com.pikaso.home.cinemanote.view.GenreDTO;
import com.pikaso.home.cinemanote.view.LocalizedGenreUpdateDTO;

@RestController
@Api(name = "Genre service", description = "Genre managment controller")
@ApiErrors(apierrors = {@ApiError(code="400", description="Request has malformed syntax")})
@RequestMapping(value = "/genre", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreService {

	@Autowired
	private GenreManager genreManager;

	@ApiMethod(description="Add new genre")
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<GenreDTO> create(@ApiBodyObject @RequestBody String genreName) {
		
		try {
			// TODO: name must be unique
			Genre genre = genreManager.create(Genre.create(genreName));
			
			return ResponseEntity.ok().body(genre.toDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	@ApiMethod(description="Edit the genre by id")
	@RequestMapping(value="/{genreId}", method = RequestMethod.PUT)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Genre not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<GenreDTO> modify(@ApiPathParam(name = "genreId", description = "the genre id") 
			@PathVariable Long genreId, @ApiBodyObject @RequestBody String genreName) {
		
		try {
			// TODO: name must be unique
			Genre genre = genreManager.modify(genreId, genreName);
			
			return ResponseEntity.ok().body(genre.toDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	@ApiMethod(description="Delete the genre by id")
	@RequestMapping(value="/{genreId}", method = RequestMethod.DELETE)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Genre not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<GenreDTO> delete(@ApiPathParam(name = "genreId", description = "the genre id") 
			@PathVariable Long genreId) {
		
		try {
			Genre genre = genreManager.delete(genreId);
			
			return ResponseEntity.ok().body(genre.toDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
	@ApiMethod(description="Add the genre's localization")
	@RequestMapping(value="/{genreId}/localization", method = RequestMethod.POST)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Genre not found"), 
			@ApiError(code="405", description="Language is not in ISO639-1 format")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<GenreDTO> addLocalization(@ApiPathParam(name = "genreId", description = "the genre id") 
			@PathVariable Long genreId, @ApiBodyObject @RequestBody LocalizedGenreUpdateDTO localizedGenreDTO) {

		ValidatorUtil.verifyLanguage(localizedGenreDTO.getLanguage());
		
		LocalizedGenre localization = LocalizedGenre.from(localizedGenreDTO);
		try {
			Genre genre = genreManager.addLocalization(genreId, localization);
			
			return ResponseEntity.ok().body(genre.toDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
	@ApiMethod(description="Remove the genre's localization")
	@RequestMapping(value="/{genreId}/localization", method = RequestMethod.DELETE)
	@ApiErrors(apierrors = {@ApiError(code="404", description="Genre not found or no such localization"), 
			@ApiError(code="405", description="Language is not in ISO639-1 format")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<GenreDTO> removeLocalization(@ApiPathParam(name = "genreId", description = "the genre id") 
			@PathVariable Long genreId, @ApiQueryParam(name = "language", description = "the language in ISO 639-1")
			@RequestParam(required = true) String language) {

		ValidatorUtil.verifyLanguage(language);
		
		try {
			Genre genre = genreManager.removeLocalization(genreId, language);
			
			return ResponseEntity.ok().body(genre.toDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new BadRequestException(e.getMessage());
		}
	}
	
	@ApiMethod(description="Find genres")
	@RequestMapping(value="/", method = RequestMethod.GET)
	@ApiErrors(apierrors = {@ApiError(code="405", description="Language is not in ISO639-1 format")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<GenreDTO[]> findByLocalization(@ApiQueryParam(name = "language", description = "the language in ISO 639-1")
			@RequestParam(required = false) String language) {
		
		if (StringUtils.isEmpty(language)) {
			language = LanguageUtil.getDefaultCode(); // TODO: read from Security user language
		} else {
			ValidatorUtil.verifyLanguage(language);
		}
		
		List<Genre> genres = genreManager.findLocalized(language);
		
		return ResponseEntity.ok().body(genres.stream().map(Genre::toDTO).toArray(size->new GenreDTO[size])); 
	}
}
