package com.pikaso.home.cinemanote.entity;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.pikaso.home.cinemanote.util.DateUtil;
import com.pikaso.home.cinemanote.view.FilmInfoDTO;
import com.pikaso.home.cinemanote.view.FilmUpdateDTO;

import lombok.Data;

/**
 * Entity for <code>FILM</code> table
 * @author pikaso
 */
@Data
@Entity
@Table(name="film")
public class Film {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="film_id")
	private long id;
	
	@Column(name="title_eng")
	private String title;
	
	@ManyToMany
	@JoinTable(name="film_genre", 
		joinColumns=@JoinColumn(name="film_id"),
		inverseJoinColumns=@JoinColumn(name="genre_id"))
	private Set<Genre> genres;
	
	@Column(name="release_date")
	private Date releaseDate;
	
	@Column(name="description_eng")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="confirmed")
	private boolean confirmed;
	
	@OneToMany
	@JoinColumn(name="film_id")
	private Set<LocalizedFilm> localizattion;
	
	public static Film from(FilmUpdateDTO dto){
		Film film = new Film();
		film.setTitle(dto.getTitle());
		film.setDescription(dto.getDescription());
		film.setReleaseDate(DateUtil.fromMilliseconds(dto.getReleaseDate()));
		film.setGenres(dto.getGenres().stream().map(Genre::from).collect(toSet()));
//		film.setUser("pikaso"); //TODO: read from Security
		
		return film;
	}
	
	public FilmInfoDTO toInfoDTO(){
		FilmInfoDTO dto = new FilmInfoDTO();
		dto.setId(id);
		dto.setTitle(title);
		dto.setDescription(description);
		dto.setGenres(genres.stream().map(Genre::toDTO).collect(toList()));
		dto.setReleaseDate(DateUtil.toMilliseconds(releaseDate));
		
		return dto;
	}
}
