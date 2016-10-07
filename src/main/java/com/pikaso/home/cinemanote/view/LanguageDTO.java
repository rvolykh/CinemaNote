package com.pikaso.home.cinemanote.view;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import lombok.Data;

/**
 * {@link Language} DTO
 * @author pikaso
 */
@Data
@ApiObject(name="Language", description="Language output object")
public class LanguageDTO {
	@ApiObjectField(description = "the language in ISO 639-1 format")
	private String code;
	@ApiObjectField(description = "the language full name")
	private String language;
}
