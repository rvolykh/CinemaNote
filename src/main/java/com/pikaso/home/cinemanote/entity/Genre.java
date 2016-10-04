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
import javax.persistence.Table;

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
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="genre_id")
	private long id;
	
	@Column(name="name_eng")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = LocalizedGenre.GENRE)
	@MapKeyColumn(name = "language", table = "loc_genre")
	private Map<String, LocalizedGenre> names = new HashMap<>();
	
	public void localize(String language){
		Optional.ofNullable(names).ifPresent(loc -> Optional.ofNullable(names.get(language)).ifPresent((e)-> {
				this.setName(e.getName());
			}));
	}
	
	public static Genre create(String name){
		Genre genre = new Genre();
		genre.setName(name);
		
		return genre;
	}
	
	public static Genre from(GenreDTO dto){
		Genre genre = new Genre();
		genre.setId(dto.getId());
		genre.setName(dto.getName());
		
		return genre;
	}
	
	public void editFrom(Genre genre){
		this.setName(genre.getName());
	}
	
	public GenreDTO toDTO(){
		GenreDTO dto = new GenreDTO();
		dto.setId(id);
		dto.setLanguage(LanguageUtil.getDefaultLanguage()); // TODO: read from security user language
		dto.setName(name);
		
		return dto;
	}
	
	public GenreDTO toDTO(String language){
		GenreDTO dto = new GenreDTO();
		dto.setId(id);
		dto.setLanguage(LanguageUtil.getLanguage(language));
		dto.setName(name);
		
		return dto;
	}
}
