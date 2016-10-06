package com.pikaso.home.cinemanote.view;

import javax.validation.constraints.NotNull;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import lombok.Data;

/**
 * {@link User} update DTO
 * @author pikaso
 */
@Data
@ApiObject(name="UserUpdate", description="User input object")
public class UserUpdateDTO {
	@NotNull
	@ApiObjectField(description = "the user name")
	private String name;
	@NotNull
	@ApiObjectField(description = "the user password")
	private String password;
	@ApiObjectField(description = "the user language")
	private String language;
}
