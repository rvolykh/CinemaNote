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

import com.pikaso.home.cinemanote.util.DateUtil;
import com.pikaso.home.cinemanote.view.BookmarkCreateDTO;
import com.pikaso.home.cinemanote.view.BookmarkDTO;

import lombok.Data;

/**
 * Entity for <code>USER_FILM</code> table
 * @author pikaso
 */
@Data
@Entity @IdClass(Bookmark.Key.class)
@Table(name="user_film")
public class Bookmark {
	
	@Id
	@ManyToOne
	@JoinColumn(name="film_id")
	private Film film;
	
	@Id
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="revision_date")
	private Date watchDate;
	
	@Column(name="message")
	private String message;
	
	@Column(name="favorite")
	private boolean favorite;
	
	@Data
	public static class Key implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private long film;
		private long user;
	}
	
	public static Key createKey(long userId, long filmId){
		Key key = new Key();
		key.setFilm(filmId);
		key.setUser(userId);
		
		return key;
	}
	
	public static Bookmark from(BookmarkCreateDTO dto, User user, Film film){
		Bookmark bookmark = new Bookmark();
		bookmark.setFavorite(dto.getIsFavorite());
		bookmark.setWatchDate(DateUtil.fromMilliseconds(dto.getWatchDate()));
		bookmark.setMessage(dto.getComment());
		bookmark.setUser(user);
		bookmark.setFilm(film);
		
		return bookmark;
	}
	
	public BookmarkDTO toDTO(){
		BookmarkDTO dto = new BookmarkDTO();
		dto.setUser(user.getName());
		dto.setComment(message);
		dto.setFilm(film.toInfoDTO());
		dto.setIsFavorite(favorite);
		dto.setWatchDate(DateUtil.toMilliseconds(watchDate));
		
		return dto;
	}
}
