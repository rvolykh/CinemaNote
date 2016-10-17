package com.pikaso.home.cinemanote.manager;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.pikaso.home.cinemanote.enumeration.FriendFilter;
import com.pikaso.home.cinemanote.enumeration.UserRole;
import com.pikaso.home.cinemanote.util.DateUtil;
import com.pikaso.home.cinemanote.util.LanguageUtil;
import com.pikaso.home.cinemanote.view.LanguageDTO;
import com.pikaso.home.cinemanote.view.PingDTO;

@Component
public class InformationManager {
	
	@Value("${jsondoc.basePath}")
	private String jsonDocPath;
	
	public PingDTO health(){
		PingDTO ping = new PingDTO();
		ping.setTime(DateUtil.now());
		ping.setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
		
		return ping;
	}

	public LanguageDTO[] getLanguages(){
		Map<String, String> languages = LanguageUtil.getCodeLanguagePairs();
		languages.remove(LanguageUtil.UNDEFINED);
		
		return languages.entrySet().stream()
				.map(x -> this.fetchLanguageDTO(x.getKey(), x.getValue())).toArray(size -> new LanguageDTO[size]);
	}
	
	public String[] getRoles(){
		return Arrays.stream(UserRole.values())
				.map(Object::toString).toArray(size -> new String[size]);
	}
	
	public String[] getFriendFilters(){
		return Arrays.stream(FriendFilter.values())
				.map(Object::toString).toArray(size -> new String[size]);
	}
	
	public String getJsonDocPath(){
		return String.format("{\"jsonDocPath\":\"%s/jsondoc-ui.html?url=%s/jsondoc#\"}", jsonDocPath, jsonDocPath);
	}
	
	private LanguageDTO fetchLanguageDTO(String code, String name){
		LanguageDTO dto = new LanguageDTO();
		dto.setCode(code);
		dto.setLanguage(name);
		
		return dto;
	}
}
