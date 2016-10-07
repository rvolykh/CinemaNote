package com.pikaso.home.cinemanote.view;

import javax.validation.constraints.NotNull;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import lombok.Data;

/**
 * {@link Bookmark} create DTO
 * @author pikaso
 */
@Data
@ApiObject(name="BookmarkCreate", description="Bookmark input object")
public class BookmarkCreateDTO {
	@NotNull
	@ApiObjectField(description = "the film id")
	private Long filmId;
	@ApiObjectField(description = "the watch date in milliseconds")
	private Long watchDate;
	@ApiObjectField(description = "the user feedback about film")
	private String comment;
	@ApiObjectField(description = "the user liked the film flag")
	private Boolean isFavorite;
}
