package com.pikaso.home.cinemanote.util;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.neovisionaries.i18n.LanguageCode;

/**
 * ISO 639-1 languages code (2 letters) util
 * @author rvolykh
 */
public class LanguageUtil {
	private static final LanguageCode defaultLanguage = LanguageCode.en;

	public static String getLanguage(String code){
		LanguageCode lang = LanguageCode.getByCodeIgnoreCase(code);
		return lang.getName();
	}

	public static List<String> getCodes(){
		return Arrays.stream(LanguageCode.values()).map(LanguageCode::getName).collect(toList());
	}
	
	public static boolean isValid(String code){
		return code.length() == 2 && Objects.nonNull(LanguageCode.getByCodeIgnoreCase(code));
	}
	
	public static boolean isNotValid(String code){
		return !isValid(code);
	}
	
	public static String getCode(String name){
		Optional<String> code = Optional.ofNullable(LanguageCode.findByName(name)).map( x-> x.stream().findFirst().get().toString() );
		return code.isPresent() ? code.get() : StringUtils.EMPTY;
	}
	
	public static String getDefaultCode(){
		return defaultLanguage.toString();
	}
	
	public static String getDefaultLanguage(){
		return defaultLanguage.getName();
	}
}
