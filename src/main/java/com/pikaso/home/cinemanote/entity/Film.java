package com.pikaso.home.cinemanote.entity;

import static java.util.stream.Collectors.toList;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pikaso.home.cinemanote.util.DateUtil;
import com.pikaso.home.cinemanote.util.LanguageUtil;
import com.pikaso.home.cinemanote.view.FilmCreateDTO;
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

	@SequenceGenerator(
			name="FILM_SEQUENCE_GENERATOR",
			sequenceName="FILM_SEQ")
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="FILM_SEQUENCE_GENERATOR")
	@Column(name = "film_id")
	private long id;

	@Column(name = "title_eng")
	private String title;

	@ManyToMany
	@JoinTable(name = "film_genre", 
		joinColumns = @JoinColumn(name = "film_id"),
		inverseJoinColumns = @JoinColumn(name = "genre_id"))
	private Set<Genre> genres = new HashSet<>();

	@Column(name = "release_date")
	private Date releaseDate;

	@Column(name = "description_eng")
	private String description;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "confirmed")
	private boolean confirmed;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = LocalizedFilm.FILM)
	@MapKeyColumn(name = "language", table = "loc_film")
	private Map<String, LocalizedFilm> localizattion = new HashMap<>();

	@Transient
	private String language;

	public void localize(String language){
		// Translate film information
		Optional.ofNullable(localizattion).ifPresent(loc -> Optional.ofNullable(loc.get(language)).ifPresent((e)->{
				this.setTitle(e.getTitle());
				this.setDescription(Optional.ofNullable(e.getDescription()).orElse(this.getDescription()));
				this.language = language;
			}));
		// Translate film genres
		Optional.ofNullable(genres).ifPresent(list -> list.stream().forEach(x->x.localize(language)));
	}

	public static Film from(FilmCreateDTO dto, Set<Genre> genres){
		Film film = new Film();
		film.setTitle(dto.getTitle());
		film.setDescription(dto.getDescription());
		film.setReleaseDate(DateUtil.fromMilliseconds(dto.getReleaseDate()));
		film.setGenres(genres);
//		film.setUser("pikaso"); //TODO: read from Security

		return film;
	}
	
	/**
	 * ATTENTION: if genres = null, than leave old value,
	 * 		if genres = emptySet(), than clear relations
	 * 		else set new genres
	 */
	public void editFrom(FilmUpdateDTO dto, Set<Genre> genres){
		this.setTitle(Optional.ofNullable(dto.getTitle()).orElse(title));
		this.setReleaseDate(Optional.ofNullable(dto.getReleaseDate())
				.map(x-> DateUtil.fromMilliseconds(x)).orElse(releaseDate));
		this.setDescription(Optional.ofNullable(dto.getDescription()).orElse(description));
		this.setGenres(Optional.ofNullable(genres).orElse(this.genres));
	}
	
	public FilmInfoDTO toInfoDTO(){
		FilmInfoDTO dto = new FilmInfoDTO();
		dto.setId(id);
		dto.setTitle(title);
		dto.setDescription(description);
		dto.setGenres(genres.stream().map(Genre::toDTO).collect(toList()));
		dto.setReleaseDate(DateUtil.toMilliseconds(releaseDate));
		dto.setLanguage(LanguageUtil.getLanguageOrDefault(language));

		return dto;
	}
}
