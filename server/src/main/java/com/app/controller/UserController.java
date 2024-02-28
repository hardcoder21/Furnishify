package com.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.exception.UserException;
import com.app.modal.User;
import com.app.response.UserProfileDTO;
import com.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/profile")
	public ResponseEntity<UserProfileDTO> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException{

		System.out.println("/api/users/profile");
		//User user=userService.findUserProfileByJwt(jwt);
		UserProfileDTO user=userService.findUserProfileDTO(jwt);
		return new ResponseEntity<UserProfileDTO>(user,HttpStatus.OK);
	}
}