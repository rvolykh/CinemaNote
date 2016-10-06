package com.pikaso.home.cinemanote.view;

import javax.validation.constraints.NotNull;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import lombok.Data;

/**
 * {@link User} create DTO
 * @author pikaso
 */
@Data
@ApiObject(name="UserCreate", description="User input object")
public class UserCreateDTO {
	@NotNull
	@ApiObjectField(description = "the user name")
	private String name;
	@NotNull
	@ApiObjectField(description = "the user email")
	private String email;
	@NotNull
	@ApiObjectField(description = "the user password")
	private String password;
	@ApiObjectField(description = "the user language")
	private String language;
}
