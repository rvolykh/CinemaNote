package com.pikaso.home.cinemanote.view;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.pikaso.home.cinemanote.entity.Bookmark;

import lombok.Data;

/**
 * {@link Bookmark} create DTO
 * @author pikaso
 */
@Data
@ApiObject(name="Bookmark", description="Bookmark output object")
public class BookmarkDTO {
	@ApiObjectField(description = "information about film")
	private FilmInfoDTO film;
	@ApiObjectField(description = "the user name")
	private String user;
	@ApiObjectField(description = "the watch date in milliseconds")
	private Long watchDate;
	@ApiObjectField(description = "the user feedback about film")
	private String comment;
	@ApiObjectField(description = "the user liked the film flag")
	private Boolean isFavorite;
}
