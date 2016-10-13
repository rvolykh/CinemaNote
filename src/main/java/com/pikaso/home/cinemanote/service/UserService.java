package com.pikaso.home.cinemanote.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiQueryParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pikaso.home.cinemanote.entity.User;
import com.pikaso.home.cinemanote.enumeration.FriendFilter;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.exception.NotFoundException;
import com.pikaso.home.cinemanote.manager.UserManager;
import com.pikaso.home.cinemanote.manager.Validator;
import com.pikaso.home.cinemanote.view.UserCreateDTO;
import com.pikaso.home.cinemanote.view.UserDTO;
import com.pikaso.home.cinemanote.view.UserUpdateDTO;

/**
 * User service
 * @author pikaso
 */
@RestController
@Api(name = "User service", description = "User managment controller")
@ApiErrors(apierrors = {@ApiError(code="400", description="Request has malformed syntax")})
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserService {

	@Autowired
	private Validator validator;
	
	@Autowired
	private UserManager userManager;

	@ApiMethod(description="Register new user")
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiErrors(apierrors = {@ApiError(code="405", description="Language is not in ISO639-1 format")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO> create(@ApiBodyObject @RequestBody UserCreateDTO userDTO) {
		
		validator.verifyLanguage(userDTO.getLanguage());

		User user = userManager.create(userDTO);

		return ResponseEntity.ok().body(user.toDTO()); 
	}

	@ApiMethod(description="Modify the user's information")
	@RequestMapping(value="/{userId}", method = RequestMethod.PUT)
	@ApiErrors(apierrors = {@ApiError(code="404", description="User not found"),
			@ApiError(code="405", description="Language is not in ISO639-1 format")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO> modify(@ApiPathParam(name="userId", description="the user id") 
			@PathVariable Long userId, @ApiBodyObject @RequestBody UserUpdateDTO userDTO) {
		
		validator.verifyLanguage(userDTO.getLanguage());

		try {
			User user = userManager.modify(userId, userDTO);
			return ResponseEntity.ok().body(user.toDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	@ApiMethod(description="Find user by id")
	@RequestMapping(value="/{userId}", method=RequestMethod.GET)
	@ApiErrors(apierrors = {@ApiError(code="404", description="User not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO> findById(@ApiPathParam(name="userId", description="the user id") 
			@PathVariable Long userId) {

		try {
			User user = userManager.find(userId);
			return ResponseEntity.ok().body(user.toDTO()); 
		} catch (CinemaNoteSelectException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	@ApiMethod(description="Find users")
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> users = userManager.find();

		return ResponseEntity.ok().body(users.stream().map(User::toDTO).collect(toList())); 
	}
	
	@ApiMethod(description="Change the user's default language")
	@RequestMapping(value="/{userId}/language", method = RequestMethod.PUT)
	@ApiErrors(apierrors = {@ApiError(code="404", description="User not found"),
			@ApiError(code="405", description="Language is not in ISO639-1 format")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO> changeLanguage(@ApiPathParam(name="userId", description="the user id") 
			@PathVariable Long userId, @ApiBodyObject @RequestBody String language) {

		validator.verifyLanguage(language);

		try {
			User user = userManager.changeLanguage(userId, language);
			return ResponseEntity.ok().body(user.toDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
	@ApiMethod(description="Change the user's role")
	@RequestMapping(value="/{userId}/role", method = RequestMethod.PUT)
	@ApiErrors(apierrors = {@ApiError(code="404", description="User not found"),
			@ApiError(code="405", description="Bad role, look information service")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO> changeRole(@ApiPathParam(name="userId", description="the user id") 
			@PathVariable Long userId, @ApiBodyObject @RequestBody String role) {

		validator.verifyRole(role);

		try {
			User user = userManager.changeRole(userId, role);
			return ResponseEntity.ok().body(user.toDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
	// TODO: replace link to /me/friend and read user from security
	@ApiMethod(description="Add a friend, return list of friends")
	@RequestMapping(value="/{userId}/friend", method = RequestMethod.POST)
	@ApiErrors(apierrors = {@ApiError(code="404", description="User not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO[]> addFriend(@ApiPathParam(name="userId", description="the user id") 
			@PathVariable Long userId, @ApiBodyObject @RequestBody(required = true) Long friendId) {

		try {
			UserDTO[] users = userManager.addFriend(userId, friendId);
			return ResponseEntity.ok().body(users); 
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
	// TODO: replace link to /me/friend and read user from security
	@ApiMethod(description="Remove a friend, return list of friends")
	@RequestMapping(value="/{userId}/friend", method = RequestMethod.DELETE)
	@ApiErrors(apierrors = {@ApiError(code="404", description="User not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO[]> removeFriend(@ApiPathParam(name="userId", description="the user id") 
			@PathVariable Long userId, @ApiQueryParam(name = "friendId", description = "the friend id") 
			@RequestParam(required = true) Long friendId) {

		try {
			UserDTO[] users = userManager.removeFriend(userId, friendId);
			return ResponseEntity.ok().body(users); 
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
	@ApiMethod(description="Get my friends")
	@RequestMapping(value="/me/friend", method = RequestMethod.GET)
	@ApiErrors(apierrors = {@ApiError(code="404", description="User not found"),
			@ApiError(code="405", description="Bad friend filter, look information service")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO[]> getMyFriends(@ApiQueryParam(name = "filter", 
			description = "look available values in Information service") @RequestParam String filter) {

		validator.verifyFriendFilter(filter);
		
		try {
			UserDTO[] users = userManager.getFriends(FriendFilter.valueOf(filter));
			return ResponseEntity.ok().body(users); 
		} catch (CinemaNoteSelectException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
	
	@ApiMethod(description="Get friends")
	@RequestMapping(value="/{userId}/friend", method = RequestMethod.GET)
	@ApiErrors(apierrors = {@ApiError(code="404", description="User not found"),
			@ApiError(code="405", description="Bad friend filter, look information service")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO[]> getFriends(@ApiPathParam(name="userId", description="the user id") 
			@PathVariable Long userId,
			@ApiQueryParam(name = "filter", description = "look available values in Information service")
			@RequestParam String filter) {

		validator.verifyFriendFilter(filter);
		
		try {
			UserDTO[] users = userManager.getFriends(userId, FriendFilter.valueOf(filter));
			return ResponseEntity.ok().body(users); 
		} catch (CinemaNoteSelectException e) {
			throw new NotFoundException(e.getMessage());
		}
	}
}
