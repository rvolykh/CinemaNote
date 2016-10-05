package com.pikaso.home.cinemanote.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pikaso.home.cinemanote.view.UserDTO;
import com.pikaso.home.cinemanote.view.UserUpdateDTO;

import lombok.Data;

/**
 * Entity for <code>USER</code> table
 * @author pikaso
 */
@Data
@Entity
@Table(name="user")
public class User {
	
	@SequenceGenerator(
			name="USER_SEQUENCE_GENERATOR", 
			sequenceName="USER_SEQ")
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="USER_SEQUENCE_GENERATOR")
	@Column(name="user_id")
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="language")
	private String language;
	
	@Column(name="role")
	private String role;
	
	public static User from(UserUpdateDTO dto){
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setLanguage(dto.getLanguage());
		user.setName(dto.getName());
		user.setPassword(dto.getPassword());
		user.setRole(Role.USER.toString());
		
		return user;
	}
	
	public UserDTO toDTO(){
		UserDTO dto = new UserDTO();
		dto.setEmail(email);
		dto.setId(id);
		dto.setLanguage(language);
		dto.setName(name);
		dto.setRole(role);
		
		return dto;
	}
	
	public enum Role {
		USER, ADMIN
	}
}
