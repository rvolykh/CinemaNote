package com.pikaso.home.cinemanote.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.pikaso.home.cinemanote.entity.User;

/**
 * Jpa repository for {@link User} entity
 * @author pikaso
 */
public interface UserRepository extends Repository<User, Long> {

	Optional<User> findOne(Long id);
	
	User save(User user);
	
	List<User>  findAll();
	
	void delete(User user);
	
	boolean exists(Long id);
}
