package com.ec.application.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ec.application.model.ContactInfo;
import com.ec.application.service.ContactInfoService;

@RestController
@RequestMapping("/contact")
public class ContactController 
{
	@Autowired
	ContactInfoService contactInfoService;
	
	@PostMapping("/create") 
	@ResponseStatus(HttpStatus.CREATED)
	public ContactInfo createCategory(@RequestBody ContactInfo payload) throws Exception
	{
		
		return contactInfoService.createContactInfo(payload);
	}
	
	@GetMapping("/isused/{id}") 
	public boolean checkIfContactIsUsed(@PathVariable long id) throws Exception
	{
		return contactInfoService.checkIfContactIsUsed(id);
	}
}
