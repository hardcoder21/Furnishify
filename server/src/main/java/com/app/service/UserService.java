package com.app.service;

import com.app.exception.UserException;
import com.app.modal.User;
import com.app.response.UserProfileDTO;

public interface UserService {
	
	public User findUserById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public UserProfileDTO findUserProfileDTO(String jwt) throws UserException;

}
