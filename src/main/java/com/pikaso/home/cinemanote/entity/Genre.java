package com.pikaso.home.cinemanote.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.pikaso.home.cinemanote.util.LanguageUtil;
import com.pikaso.home.cinemanote.view.GenreDTO;

import lombok.Data;

/**
 * Entity for <code>GENRE</code> table
 * @author pikaso
 */
@Data
@Entity
@Table(name="genre")
public class Genre {
	
	@SequenceGenerator(
			name="GENRE_SEQUENCE_GENERATOR",
			sequenceName="GENRE_SEQ")
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="GENRE_SEQUENCE_GENERATOR")
	@Column(name="genre_id")
	private long id;
	
	@Column(name="name_eng")
	private String name;
	
	@Transient
	private String language;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = LocalizedGenre.GENRE)
	@MapKeyColumn(name = "language", table = "loc_genre")
	private Map<String, LocalizedGenre> names = new HashMap<>();
	
	public void localize(String language){
		Optional.ofNullable(names).ifPresent(loc -> Optional.ofNullable(names.get(language)).ifPresent((e)-> {
				this.setName(e.getName());
				this.language = language;
			}));
	}
	
	public static Genre create(String name){
		Genre genre = new Genre();
		genre.setName(name);
		
		return genre;
	}
	
	public void editFrom(String name){
		this.setName(Optional.ofNullable(name).orElse(this.name));
	}
	
	public GenreDTO toDTO(){
		GenreDTO dto = new GenreDTO();
		dto.setId(id);
		dto.setLanguage(LanguageUtil.getLanguageOrDefault(language)); // TODO: read from security user language
		dto.setName(name);
		
		return dto;
	}
}
