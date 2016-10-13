package com.pikaso.home.cinemanote.manager;

import org.springframework.stereotype.Component;

import com.pikaso.home.cinemanote.enumeration.FriendFilter;
import com.pikaso.home.cinemanote.enumeration.UserRole;
import com.pikaso.home.cinemanote.exception.BadRequestException;
import com.pikaso.home.cinemanote.util.LanguageUtil;

@Component
public class Validator {
	
	public void verifyLanguage(String code){
		if(LanguageUtil.isNotValid(code)){
			throw new BadRequestException("Language must be in ISO639-1 format (2 letter)");
		}
	}

	public void verifyRole(String role){
		UserRole.from(role)
				.orElseThrow(() -> new BadRequestException("The role " + role + " is not exist"));
	}
	
	public void verifyFriendFilter(String filter){
		FriendFilter.from(filter)
				.orElseThrow(() -> new BadRequestException("The friend filter " + filter + " is not exist"));
	}
}
