package com.pikaso.home.cinemanote.view;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import lombok.Data;

/**
 * {@link User} DTO
 * @author pikaso
 */
@Data
@ApiObject(name="User", description="User output view object")
public class UserDTO {
	@ApiObjectField(description = "the user id")
	private long id;
	@ApiObjectField(description = "the user name")
	private String name;
	@ApiObjectField(description = "the user email")
	private String email;
	@ApiObjectField(description = "the user language")
	private String language;
	@ApiObjectField(description = "the user role")
	private String role;
}
