package com.pikaso.home.cinemanote.manager;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pikaso.home.cinemanote.entity.User;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.jpa.UserRepository;
import com.pikaso.home.cinemanote.util.LanguageUtil;
import com.pikaso.home.cinemanote.view.UserCreateDTO;
import com.pikaso.home.cinemanote.view.UserUpdateDTO;

@Component
public class UserManager {
	private static final Logger log = LoggerFactory.getLogger(UserManager.class);
	
	@Autowired
	private UserRepository userRepository;
	
	public User create(UserCreateDTO userDTO){
		User user = User.from(userDTO);
		
		log.info("Registered new user {}, {}", userDTO.getName(), userDTO.getEmail());
		
		return userRepository.save(user);
	}
	
	public User modify(long userId, UserUpdateDTO userDTO) throws CinemaNoteUpdateException {
		User user = userRepository.findOne(userId)
				.orElseThrow(() -> new CinemaNoteUpdateException("Cannot modify none existing user " + userId));
		
		user.editFrom(userDTO);
		
		log.info("User {} change information about user {}, n-p-l: {}-{}-{}", "CinemaNote", userDTO.getName(),
				userDTO.getName()!=null? true: false, 
						userDTO.getPassword()!=null? true: false,
								userDTO.getLanguage()!=null? true: false); // TODO: Read from security
		
		return userRepository.save(user);
	}
	
	public User find(long id) throws CinemaNoteSelectException {
		return userRepository.findOne(id)
				.orElseThrow(() -> new CinemaNoteSelectException("Cannot find user with id " + id));
	}
	
	public List<User> find(){
		return userRepository.findAll();
	}
	
	public User changeLanguage(long userId, String language) throws CinemaNoteUpdateException {
		User user = userRepository.findOne(userId)
				.orElseThrow(() -> new CinemaNoteUpdateException("Cannot find user with id " + userId));
		
		user.setLanguage(language);
		
		log.info("User {} changed default language {} for {}", "CinemaNote", LanguageUtil.getLanguage(language), user.getName());
		
		return userRepository.save(user);
	}
	
	public User changeRole(long userId, String role) throws CinemaNoteUpdateException {
		User user = userRepository.findOne(userId)
				.orElseThrow(() -> new CinemaNoteUpdateException("Cannot find user with id " + userId));
		
		user.setRole(role);
		
		log.info("User {} changed role {} for {}", "CinemaNote", role, user.getName()); // TODO: read from Security
		
		return userRepository.save(user);
	}
}
