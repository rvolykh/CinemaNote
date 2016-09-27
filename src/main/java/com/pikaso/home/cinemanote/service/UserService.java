package com.pikaso.home.cinemanote.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pikaso.home.cinemanote.entity.User;
import com.pikaso.home.cinemanote.exception.CinemaNoteSelectException;
import com.pikaso.home.cinemanote.exception.CinemaNoteUpdateException;
import com.pikaso.home.cinemanote.exception.NotFoundException;
import com.pikaso.home.cinemanote.manager.UserManager;
import com.pikaso.home.cinemanote.view.UserDTO;
import com.pikaso.home.cinemanote.view.UserUpdateDTO;

/**
 * User service
 * @author pikaso
 */
@RestController
@Api(description = "The user controller", name = "User service")
@ApiErrors(apierrors = {@ApiError(code="405", description="Bad input parameters")})
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserService {

	@Autowired
	private UserManager userManager;

	/**
	 * Register user
	 * @param userDTO user update DTO
	 * @return the registered user
	 */
	@ApiMethod(description="Register new user")
	@RequestMapping(value="/", method = RequestMethod.POST)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO> create(@ApiBodyObject @RequestBody UserUpdateDTO userDTO) {
		User user = User.from(userDTO);

		user = userManager.create(user);

		return ResponseEntity.ok().body(user.toDTO()); 
	}

	/**
	 * Modify user information
	 * @param userId the user id to modify
	 * @param userDTO user update DTO
	 * @return the updated user
	 */
	@ApiMethod(description="Modify user information")
	@RequestMapping(value="/{userId}", method = RequestMethod.PUT)
	@ApiErrors(apierrors = {@ApiError(code="404", description="User with given id not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO> modify(@ApiPathParam(name="userId", description="the user id") 
	@PathVariable Long userId, @ApiBodyObject @RequestBody UserUpdateDTO userDTO) {

		User user = User.from(userDTO);

		try {
			user = userManager.modify(userId, user);
			return ResponseEntity.ok().body(user.toDTO()); 
		} catch (CinemaNoteUpdateException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	/**
	 * Find user by id
	 * @param userId the user id to find
	 * @return found user
	 */
	@ApiMethod(description="Find user by id")
	@RequestMapping(value="/{userId}", method=RequestMethod.GET)
	@ApiErrors(apierrors = {@ApiError(code="404", description="User with given id not found")})
	@ApiResponseObject @ResponseBody
	public ResponseEntity<UserDTO> findById(@ApiPathParam(name="userId", description="the user id") @PathVariable Long userId) {

		try {
			User user = userManager.find(userId);
			return ResponseEntity.ok().body(user.toDTO()); 
		} catch (CinemaNoteSelectException e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	/**
	 * Find all users
	 * @return found users
	 */
	@ApiMethod(description="Find all users")
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ApiResponseObject @ResponseBody
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> users = userManager.find();

		return ResponseEntity.ok().body(users.stream().map(User::toDTO).collect(toList())); 
	}
}
