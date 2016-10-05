package com.pikaso.home.cinemanote.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pikaso.home.cinemanote.entity.User;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.jpa.UserRepository;

@Component
public class UserManager {
	
	@Autowired
	private UserRepository userRepository;
	
	public User create(User user){
		return userRepository.save(user);
	}
	
	public User modify(long userId, User user) throws CinemaNoteUpdateException {
		if(!userRepository.exists(userId)){
			throw new CinemaNoteUpdateException("Cannot modify none existing user " + userId);
		}
		user.setId(userId);
		
		return userRepository.save(user);
	}
	
	public User find(long id) throws CinemaNoteSelectException {
		return userRepository.findOne(id)
				.orElseThrow(() -> new CinemaNoteSelectException("Cannot find user with id " + id));
	}
	
	public List<User> find(){
		return userRepository.findAll();
	}
}
