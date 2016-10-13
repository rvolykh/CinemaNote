package com.pikaso.home.cinemanote.enumeration;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public enum UserRole {
	USER, ADMIN;

	public static Optional<UserRole> from(String value){
		if(StringUtils.isNoneEmpty(value)){
			try {
				return Optional.of(UserRole.valueOf(value));
			} catch(IllegalArgumentException ex) {
				// return Optional.empty();
			}
		}

		return Optional.empty();
	}
}
