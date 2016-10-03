package com.pikaso.home.cinemanote.util;

import com.pikaso.home.cinemanote.exception.BadRequestException;

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
	
}
