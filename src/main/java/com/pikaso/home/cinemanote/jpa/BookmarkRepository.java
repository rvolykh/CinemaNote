package com.pikaso.home.cinemanote.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pikaso.home.cinemanote.entity.Bookmark;

/**
 * Jpa repository for {@link Bookmark} entity
 * @author pikaso
 */
public interface BookmarkRepository extends JpaRepository<Bookmark, Bookmark.Key> {
	
	List<Bookmark> findByUserId(Long userId);
	
	List<Bookmark> findByUserIdAndFilmGenresId(Long userId, Long genreId);
}
