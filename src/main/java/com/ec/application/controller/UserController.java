package com.ec.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ec.application.data.CreateUserData;
import com.ec.application.data.ResetPasswordData;
import com.ec.application.model.User;
import com.ec.application.service.UserService;

@RestController
@RequestMapping("ec/user")
public class UserController 
{

	@Autowired
	UserService userService;
	
	@GetMapping
	public Page<User> returnAllPayments(Pageable pageable) 
	{
		
		return userService.fetchAll(pageable);
	}
	@PostMapping("/create") 
	@ResponseStatus(HttpStatus.CREATED)
	public User createUser(@RequestBody CreateUserData payload) throws Exception{
		
		return userService.createUser(payload);
	}
	
	@PostMapping("/updatepassword") 
	@ResponseStatus(HttpStatus.OK)
	public User updateUser(@RequestBody ResetPasswordData payload) throws Exception{
		
		return userService.updateUser(payload);
	}
}
