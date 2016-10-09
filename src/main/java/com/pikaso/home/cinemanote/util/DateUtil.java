package com.pikaso.home.cinemanote.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Util to manage Date interaction
 * @author pikaso
 */
public final class DateUtil {
	private DateUtil(){}
	
	public static Date now(){
		return toDate(LocalDateTime.now());
	}
	
	public static Date fromMilliseconds(Long milliseconds){
		if(milliseconds != null){
			return new Date(milliseconds);
		}
		return null;
	}
	
	public static Long toMilliseconds(Date date){
		if(date != null){
			return date.getTime();
		}
		return null;
	}
	
	private static Date toDate(LocalDateTime localDate){
		return Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	@SuppressWarnings("unused")
	private static LocalDateTime fromDate(Date date){
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
}
