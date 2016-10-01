package com.pikaso.home.cinemanote.util;

import org.springframework.util.DigestUtils;

/**
 * Util to manage MD5 hash values
 * @author pikaso
 */
public final class MD5Util {
	private MD5Util(){}

	public static String hash(String text){
		return DigestUtils.md5DigestAsHex(text.getBytes()).toString();
	}
	
	public static boolean validate(String password, String hash){
		return hash.equalsIgnoreCase(MD5Util.hash(password));
	}
}
