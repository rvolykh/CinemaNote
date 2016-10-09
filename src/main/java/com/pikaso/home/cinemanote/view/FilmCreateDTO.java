package com.pikaso.home.cinemanote.view;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import lombok.Data;

/**
 * {@link Film} create DTO
 * @author pikaso
 */
@Data
@ApiObject(name="FilmCreate", description="Film input object")
public class FilmCreateDTO {
	@NotNull
	@ApiObjectField(description = "the film title")
	private String title;
	@ApiObjectField(description = "the film genres ids")
	private Collection<Long> genreIds;
	@ApiObjectField(description = "the film release date in milliseconds")
	private Long releaseDate;
	@ApiObjectField(description = "the film description")
	private String description;
}
