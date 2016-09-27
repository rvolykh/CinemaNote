package com.pikaso.home.cinemanote.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Entity for <code>LOC_GENRE</code> table
 * @author pikaso
 */
@Data
@Entity @IdClass(LocalizedGenre.Key.class)
@Table(name="loc_genre")
public class LocalizedGenre {
	
	@Id
	@ManyToOne
	@JoinColumn(name="genre_id")
	private Genre genre;
	
	@Id
	@Column(name="language")
	private String language;
	
	@Column(name="name")
	private String name;
	
	@Data
	public class Key implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private long genre;
		private String language;
	}
}
