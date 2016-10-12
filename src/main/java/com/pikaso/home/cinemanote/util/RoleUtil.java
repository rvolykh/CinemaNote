package com.pikaso.home.cinemanote.util;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public class RoleUtil {
	public enum Role {
		USER, ADMIN;

		public static Optional<Role> from(String value){
			if(StringUtils.isNoneEmpty(value)){
				try {
					return Optional.of(Role.valueOf(value));
				} catch(IllegalArgumentException ex) {
					// return Optional.empty();
				}
			}

			return Optional.empty();
		}
	}
	
	public static String[] getRoles(){
		return Arrays.stream(Role.values())
				.map(Object::toString).toArray(size -> new String[size]);
	}
}
