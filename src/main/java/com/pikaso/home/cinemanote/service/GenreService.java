package com.pikaso.home.cinemanote.service;

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

import com.pikaso.home.cinemanote.entity.Genre;
import com.pikaso.home.cinemanote.exception.BadRequestException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.manager.GenreManager;
import com.pikaso.home.cinemanote.view.GenreDTO;

@RestController
@Api(description = "The genre controller", name = "Genre service")
@ApiErrors(apierrors = {@ApiError(code="405", description="Bad input parameters")})
@RequestMapping(value = "/genre", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreService {

	@Autowired
	private GenreManager genreManager;

	@ApiMethod(description="Add new genre")
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<GenreDTO> create(@ApiBodyObject @RequestBody String genreName) {
		Genre genre = new Genre();
		genre.setName(genreName);

		try {
			genre = genreManager.create(genre);
			return ResponseEntity.ok().body(genre.toDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@ApiMethod(description="Delete genre by id")
	@RequestMapping(value="/{genreId}", method = RequestMethod.DELETE)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<GenreDTO> delete(@ApiPathParam(name = "genreId", description = "the genre id") @PathVariable Long genreId) {
		try {
			Genre genre = genreManager.delete(genreId);
			return ResponseEntity.ok().body(genre.toDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@ApiMethod(description="Find all genres")
	@RequestMapping(value="/", method = RequestMethod.GET)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<GenreDTO[]> findAll() {
		List<Genre> genres = genreManager.findAll();
		return ResponseEntity.ok().body(genres.stream().map(Genre::toDTO).toArray(size->new GenreDTO[size])); 
	}
}
