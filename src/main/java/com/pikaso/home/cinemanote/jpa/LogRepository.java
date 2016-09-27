package com.pikaso.home.cinemanote.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pikaso.home.cinemanote.entity.Log;

/**
 * Jpa repository for {@link Log} entity
 * @author pikaso
 */
public interface LogRepository extends JpaRepository<Log, Long>{

}
