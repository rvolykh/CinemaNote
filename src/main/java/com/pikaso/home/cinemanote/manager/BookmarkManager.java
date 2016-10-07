package com.pikaso.home.cinemanote.manager;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pikaso.home.cinemanote.entity.Bookmark;
import com.pikaso.home.cinemanote.entity.Film;
import com.pikaso.home.cinemanote.entity.User;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.jpa.BookmarkRepository;
import com.pikaso.home.cinemanote.view.BookmarkCreateDTO;
import com.pikaso.home.cinemanote.view.BookmarkDTO;

@Component
public class BookmarkManager {
	private static final String SELECT_EMSG_TEMPLATE = "Cannot find film %s for user %s";
	
	@Autowired
	private BookmarkRepository bookmarkRepository;
	
	@Autowired
	private FilmManager filmManager;
	
	@Autowired
	private UserManager userManager;
	
	private static final Long MOCK_USER_ID = 1L; // TODO: read userId from Security
	
	@Transactional
	public BookmarkDTO save(BookmarkCreateDTO bookmarkDTO) throws CinemaNoteUpdateException {
		Film film = null;
		User user = null;
		
		try {
			film = filmManager.findOriginal(bookmarkDTO.getFilmId());
			user = userManager.find(MOCK_USER_ID);
		} catch(CinemaNoteSelectException e) {
			throw new CinemaNoteUpdateException("Cannot save bookmark: " + e.getMessage());
		}
		
		Bookmark bookmark = Bookmark.from(bookmarkDTO, user, film);
		
		return bookmarkRepository.save(bookmark)
				.toDTO();
	}
	
	public BookmarkDTO findMyByFilmId(Long filmId) throws CinemaNoteSelectException {
		return Optional.ofNullable(bookmarkRepository.findOne(Bookmark.createKey(MOCK_USER_ID, filmId)))
				.map(Bookmark::toDTO).orElseThrow(() -> 
						new CinemaNoteSelectException(String.format(SELECT_EMSG_TEMPLATE, filmId, MOCK_USER_ID)));
	}
	
	public List<BookmarkDTO> findMy(Long genreId){
		if(genreId > 0){
			return bookmarkRepository.findByUserIdAndFilmGenresId(MOCK_USER_ID, genreId)
					.stream().map(Bookmark::toDTO).collect(toList());
		}
		
		return bookmarkRepository.findByUserId(MOCK_USER_ID)
				.stream().map(Bookmark::toDTO).collect(toList());
	}
	
	public BookmarkDTO findFriendByFilmId(Long friendId, Long filmId) throws CinemaNoteSelectException {
		return Optional.ofNullable(bookmarkRepository.findOne(Bookmark.createKey(friendId, filmId)))
				.map(Bookmark::toDTO).orElseThrow(() -> 
						new CinemaNoteSelectException(String.format(SELECT_EMSG_TEMPLATE, filmId, friendId)));
	}
	
	public List<BookmarkDTO> findFriend(Long friendId, Long genreId){
		if(genreId > 0){
			return bookmarkRepository.findByUserIdAndFilmGenresId(friendId, genreId)
					.stream().map(Bookmark::toDTO).collect(toList());
		}
		
		return bookmarkRepository.findByUserId(friendId)
				.stream().map(Bookmark::toDTO).collect(toList());
	}
}
