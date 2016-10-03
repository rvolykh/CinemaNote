package com.pikaso.home.cinemanote.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.pikaso.home.cinemanote.view.LocalizedGenreUpdateDTO;

import lombok.Data;

/**
 * Entity for <code>LOC_GENRE</code> table
 * @author pikaso
 */
@Data
@Entity @IdClass(LocalizedGenre.Key.class)
@Table(name="loc_genre")
public class LocalizedGenre {
	protected static final String GENRE = "genreId";
	
	@Id
	@Column(name="genre_id")
	private long genreId;
	
	@Id
	@Column(name="language")
	private String language;
	
	@Column(name="name")
	private String name;
	
	public static LocalizedGenre from(LocalizedGenreUpdateDTO dto){
		LocalizedGenre localizedGenre = new LocalizedGenre();
		localizedGenre.setLanguage(dto.getLanguage());
		localizedGenre.setName(dto.getName());
		
		return localizedGenre;
	}
	
	@Data
	public static class Key implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private long genreId;
		private String language;
	}
}
