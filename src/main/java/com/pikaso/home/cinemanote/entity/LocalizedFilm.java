package com.pikaso.home.cinemanote.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.pikaso.home.cinemanote.view.LocalizedFilmUpdateDTO;

import lombok.Data;

/**
 * Entity for <code>LOC_FILM</code> table
 * @author pikaso
 */
@Data
@Entity @IdClass(LocalizedFilm.Key.class)
@Table(name="loc_film")
public class LocalizedFilm {
	protected static final String FILM = "filmId";
	
	@Id
	@Column(name="film_Id")
	private long filmId;

	@Id
	@Column(name="language")
	private String language;

	@Column(name="title")
	private String title;

	@Column(name="description")
	private String description;

	public static LocalizedFilm from(LocalizedFilmUpdateDTO dto){
		LocalizedFilm localization = new LocalizedFilm();
		localization.setTitle(dto.getTitle());
		localization.setLanguage(dto.getLanguage());
		localization.setDescription(dto.getDescription());

		return localization;
	}

	@Data
	public static class Key implements Serializable {
		private static final long serialVersionUID = 1L;

		private long filmId;
		private String language;
	}
}
