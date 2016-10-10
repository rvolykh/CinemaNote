package com.pikaso.home.cinemanote.service;

import java.util.List;

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

import com.pikaso.home.cinemanote.exception.BadRequestException;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.manager.BookmarkManager;
import com.pikaso.home.cinemanote.view.BookmarkCreateDTO;
import com.pikaso.home.cinemanote.view.BookmarkDTO;

@RestController
@Api(name = "Bookmarks service", description = "Manage user bookmarks")
@ApiErrors(apierrors = {@ApiError(code="400", description="Request has malformed syntax")})
@RequestMapping(value = "/bookmark", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookmarkService {

	@Autowired
	private BookmarkManager bookmarkManager;

	@ApiMethod(description="Add new bookmark")
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiErrors(apierrors = {@ApiError(code="405", description="Film with given id doesn't exist")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<BookmarkDTO> add(@ApiBodyObject @RequestBody BookmarkCreateDTO bookmarkDTO) {

		try {
			BookmarkDTO bookmark = bookmarkManager.save(bookmarkDTO);

			return ResponseEntity.ok().body(bookmark); 
		} catch (CinemaNoteUpdateException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@ApiMethod(description="Find my bookmark by id from my lists")
	@RequestMapping(value="/my/film/{filmId}", method = RequestMethod.GET)
	@ApiErrors(apierrors = {@ApiError(code="405", description="Film with given id doesn't exist")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<BookmarkDTO> findMyByFilmId(@ApiPathParam(name = "filmId", description = "the film id to find")
			@PathVariable Long filmId) {

		try {
			BookmarkDTO bookmark = bookmarkManager.findMyByFilmId(filmId);

			return ResponseEntity.ok().body(bookmark); 
		} catch (CinemaNoteSelectException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@ApiMethod(description="Find my bookmarks optionally filtered by genre")
	@RequestMapping(value="/my/film", method = RequestMethod.GET)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<BookmarkDTO[]> findMy(@ApiQueryParam(name = "genre", description = "the genre id to filter")
			@RequestParam(defaultValue = "0", required = false, name = "genre") Long genreId) {

		List<BookmarkDTO> bookmarks = bookmarkManager.findMy(genreId);

		return ResponseEntity.ok().body(bookmarks.toArray(new BookmarkDTO[bookmarks.size()])); 
	}

	@ApiMethod(description="Find friend bookmark by id from his public list")
	@RequestMapping(value="/{userId}/film/{filmId}", method = RequestMethod.GET)
	@ApiErrors(apierrors = {@ApiError(code="405", description="Film or user with given id doesn't exist")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<BookmarkDTO> findFriendByFilmId(@ApiPathParam(name = "userId", description = "friend id") @PathVariable Long userId, 
			@ApiPathParam(name = "filmId", description = "the film id to find") @PathVariable Long filmId) {

		try {
			BookmarkDTO bookmark = bookmarkManager.findFriendByFilmId(userId, filmId);

			return ResponseEntity.ok().body(bookmark); 
		} catch (CinemaNoteSelectException e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@ApiMethod(description="Find friend bookmark from public list optionally filtered by genre")
	@RequestMapping(value="/{userId}/film", method = RequestMethod.GET)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<BookmarkDTO[]> findFriend(@ApiPathParam(name = "userId", description = "friend id") @PathVariable Long userId,
			@ApiQueryParam(name = "genre", description = "the genre id to filter")
			@RequestParam(defaultValue = "0", required = false, name = "genre") Long genreId) {

		List<BookmarkDTO> bookmarks = bookmarkManager.findFriend(userId, genreId);

		return ResponseEntity.ok().body(bookmarks.toArray(new BookmarkDTO[bookmarks.size()])); 
	}
}
