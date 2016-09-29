package com.pikaso.home.cinemanote.view;

import javax.validation.constraints.NotNull;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import lombok.Data;

/**
 * {@link LocalizedFilm} DTO
 * @author pikaso
 */
@Data
@ApiObject(name="LocalizedFilm", description="Film localization input view")
public class LocalizedFilmUpdateDTO {
	@NotNull
	@ApiObjectField(description = "the language in ISO 639-1 format")
	private String language;
	@NotNull
	@ApiObjectField(description = "the title localization")
	private String title;
	@ApiObjectField(description = "the description localization")
	private String description;
}
