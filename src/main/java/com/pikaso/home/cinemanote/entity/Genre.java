package com.pikaso.home.cinemanote.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	@OneToMany
	@JoinColumn(name="genre_id")
	private Set<LocalizedGenre> names;
	
	public static Genre from(GenreDTO dto){
		Genre genre = new Genre();
		genre.setId(dto.getId());
		genre.setName(dto.getName());
		
		return genre;
	}
	
	public GenreDTO toDTO(){
		GenreDTO dto = new GenreDTO();
		dto.setId(id);
		dto.setName(name);
		
		return dto;
	}
}
