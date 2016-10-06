package com.pikaso.home.cinemanote.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.pikaso.home.cinemanote.view.UserDTO;
import com.pikaso.home.cinemanote.view.UserUpdateDTO;
import com.pikaso.home.cinemanote.view.UserCreateDTO;

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

	public static User from(UserCreateDTO dto){
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setLanguage(dto.getLanguage());
		user.setName(dto.getName());
		user.setPassword(dto.getPassword());
		user.setRole(Role.USER.toString());

		return user;
	}
	
	public void editFrom(UserUpdateDTO dto){
		this.setLanguage(Optional.ofNullable(dto.getLanguage()).orElse(language));
		this.setName(Optional.ofNullable(dto.getName()).orElse(name));
		this.setPassword(Optional.ofNullable(dto.getPassword()).orElse(password));
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
		USER, ADMIN;

		public static Optional<Role> from(String value){
			if(StringUtils.isNoneEmpty(value)){
				try {
					return Optional.of(Role.valueOf(value));
				} catch(IllegalArgumentException ex) {
					// return Optional.empty();
				}
			}
			
			return Optional.empty();
		}
	}
}
