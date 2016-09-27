package com.pikaso.home.cinemanote.util;

import org.springframework.util.DigestUtils;

/**
 * Util to manage MD5 hash values
 * @author pikaso
 */
public final class MD5Util {
	private MD5Util(){}

	public static String hash(String text){
//		byte byteData[] = DigestUtils.md5Digest(text.getBytes());
//
//		//convert the byte to hex format method 1
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < byteData.length; i++) {
//			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
//		}
//
//		return sb.toString();
		return DigestUtils.md5DigestAsHex(text.getBytes()).toString();
	}
	
	public static boolean validate(String password, String hash){
		return hash.equalsIgnoreCase(MD5Util.hash(password));
	}
}
