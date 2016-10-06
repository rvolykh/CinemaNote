package com.pikaso.home.cinemanote.view;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.pikaso.home.cinemanote.entity.Genre;

import lombok.Data;

/**
 * {@link Genre} DTO
 * @author pikaso
 */
@Data
@ApiObject(name="Genre", description="Genre output object")
public class GenreDTO {
	@ApiObjectField(description = "the genre id")
	private long id;
	@ApiObjectField(description = "the language in ISO 639-1 format")
	private String language;
	@ApiObjectField(description = "the genre value")
	private String name;
}
