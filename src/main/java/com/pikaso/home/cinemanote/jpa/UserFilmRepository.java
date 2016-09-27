package com.pikaso.home.cinemanote.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pikaso.home.cinemanote.entity.UserFilm;

/**
 * Jpa repository for {@link UserFilm} entity
 * @author pikaso
 */
public interface UserFilmRepository extends JpaRepository<UserFilm, UserFilm.Key> {

}
