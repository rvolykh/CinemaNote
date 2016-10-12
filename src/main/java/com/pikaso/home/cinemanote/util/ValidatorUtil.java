package com.pikaso.home.cinemanote.util;

import com.pikaso.home.cinemanote.exception.BadRequestException;
import com.pikaso.home.cinemanote.util.FriendFilterUtil.Filter;
import com.pikaso.home.cinemanote.util.RoleUtil.Role;

/**
 * Service Validation util, throw runtime exceptions
 * @author rvolykh
 */
public class ValidatorUtil {

	public static void verifyLanguage(String code){
		if(LanguageUtil.isNotValid(code)){
			throw new BadRequestException("Language must be in ISO639-1 format (2 letter)");
		}
	}

	public static void verifyRole(String role){
		Role.from(role)
				.orElseThrow(() -> new BadRequestException("The role " + role + " is not exist"));
	}
	
	public static void verifyFriendFilter(String filter){
		Filter.from(filter)
				.orElseThrow(() -> new BadRequestException("The friend filter " + filter + " is not exist"));
	}
}
