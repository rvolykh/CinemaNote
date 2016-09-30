package com.pikaso.home.cinemanote.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Entity for <code>USER_FILM</code> table
 * @author pikaso
 */
@Data
@Entity @IdClass(UserFilm.Key.class)
@Table(name="user_film")
public class UserFilm {
	
	@Id
	@ManyToOne
	@JoinColumn(name="film_id")
	private Film film;
	
	@Id
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="revision_date")
	private Date revisionDate;
	
	@Column(name="message")
	private String message;
	
	@Column(name="favorite")
	private boolean favorite;
	
	@Data
	public class Key implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private long film;
		private long user;
	}
}
