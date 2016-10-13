package com.pikaso.home.cinemanote.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.pikaso.home.cinemanote.enumeration.UserRole;
import com.pikaso.home.cinemanote.view.UserCreateDTO;
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

	@ManyToMany
	@JoinTable(name = "friend", 
			joinColumns = {@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "friend_id")})
	private Set<User> myFriends = new HashSet<>();

	@ManyToMany(mappedBy = "myFriends")
	private Set<User> iAmFriendOf = new HashSet<>();
	
	public List<User> getFilteredFriends(Predicate<User> filter){
		return this.getMyFriends().stream()
				.filter(filter).collect(Collectors.toList());
	}
	
	public Predicate<User> isAcceptedFriend(){
		return this.getIAmFriendOf()::contains;
	}
	
	public Predicate<User> isRequestedFriend(){
		return isAcceptedFriend().negate();
	}
	
	public static User from(UserCreateDTO dto){
		User user = new User();
		user.setEmail(dto.getEmail());
		user.setLanguage(dto.getLanguage());
		user.setName(dto.getName());
		user.setPassword(dto.getPassword());
		user.setRole(UserRole.USER.toString());

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

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj instanceof User) {
			User user = (User) obj;
			
			return id == user.id &&
					Objects.equals(name, user.name) &&
					Objects.equals(email, user.email);
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, email);
	}
}