package com.pikaso.home.cinemanote.util;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

public class FriendFilterUtil {
	public enum Filter {
		ACCEPTED, REQUESTED, FOLLOWERS;

		public static Optional<Filter> from(String value){
			if(StringUtils.isNoneEmpty(value)){
				try {
					return Optional.of(Filter.valueOf(value));
				} catch(IllegalArgumentException ex) {
					// return Optional.empty();
				}
			}

			return Optional.empty();
		}
	}
	
	public static String[] getRoles(){
		return Arrays.stream(Filter.values())
				.map(Object::toString).toArray(size -> new String[size]);
	}
}
