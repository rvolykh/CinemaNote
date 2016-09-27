package com.pikaso.home.cinemanote.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Entity for <code>LOG</code> table
 * @author pikaso
 */
@Data
@Entity
@Table(name="log")
public class Log {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="log_id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="film_id")
	private Film film;
	
	@ManyToOne
	@JoinColumn(name="genre_id")
	private Genre genre;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="action")
	private String action;
	
	@Column(name="date")
	private Date date;
}
