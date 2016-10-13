package com.pikaso.home.cinemanote.enumeration;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.pikaso.home.cinemanote.entity.User;
import com.pikaso.home.cinemanote.jpa.UserRepository;
import com.pikaso.home.cinemanote.view.UserDTO;

public enum FriendFilter {
	ACCEPTED {
		public UserDTO[] apply(UserRepository repo, User user){
			return user.getFilteredFriends(user.isAcceptedFriend())
					.stream().map(User::toDTO).toArray(size -> new UserDTO[size]);
		}
	}, REQUESTED {
		public UserDTO[] apply(UserRepository repo, User user){
			return user.getFilteredFriends(user.isRequestedFriend())
					.stream().map(User::toDTO).toArray(size -> new UserDTO[size]);
		}
	}, FOLLOWERS {
		public UserDTO[] apply(UserRepository repo, User user){
			return repo.findByMyFriendsId(user.getId())
					.stream().filter(x -> !user.getMyFriends().contains(x))
					.map(User::toDTO).toArray(size -> new UserDTO[size]);
		}
	};

	public static Optional<FriendFilter> from(String value){
		if(StringUtils.isNoneEmpty(value)){
			try {
				return Optional.of(FriendFilter.valueOf(value));
			} catch(IllegalArgumentException ex) {
				// return Optional.empty();
			}
		}

		return Optional.empty();
	}
	
	public abstract UserDTO[] apply(UserRepository repo, User user);
}
