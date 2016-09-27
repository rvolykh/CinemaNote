package com.pikaso.home.cinemanote.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pikaso.home.cinemanote.entity.User;

/**
 * Jpa repository for {@link User} entity
 * @author pikaso
 */
public interface UserRepository extends JpaRepository<User, Long> {

}
