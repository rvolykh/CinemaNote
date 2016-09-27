package com.pikaso.home.cinemanote.view;

import java.util.Collection;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import lombok.Data;

/**
 * Short {@link Film} DTO
 * @author pikaso
 */
@Data
@ApiObject(name="FilmInfo", description="Film output view object")
public class FilmInfoDTO {
	@ApiObjectField(description = "the film id")
	private Long id;
	@ApiObjectField(description = "the film title")
	private String title;
	@ApiObjectField(description = "the film genres")
	private Collection<GenreDTO> genres;
	@ApiObjectField(description = "the film release date in milliseconds")
	private Long releaseDate;
	@ApiObjectField(description = "the film description")
	private String description;
}
