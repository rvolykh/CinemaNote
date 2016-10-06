package com.pikaso.home.cinemanote.view;

import javax.validation.constraints.NotNull;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import lombok.Data;

/**
 * {@link LocalizedGenere} update DTO
 * @author pikaso
 */
@Data
@ApiObject(name="LocalizedGenere", description="Genre localization input")
public class LocalizedGenreUpdateDTO {
	@NotNull
	@ApiObjectField(description = "the language in ISO 639-1 format")
	private String language;
	@NotNull
	@ApiObjectField(description = "the name localization")
	private String name;
}
