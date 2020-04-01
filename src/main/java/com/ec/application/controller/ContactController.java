package com.ec.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ec.application.model.BasicEntities.Contact;
import com.ec.application.service.ContactService;

@RestController
@RequestMapping("inventory/contact")
public class ContactController 
{
	@Autowired
	ContactService contactService;
	
	@GetMapping
	public Page<Contact> returnAllPayments(@RequestParam(name="page",required = false) Integer page,@RequestParam(name="size",required = false) Integer size) 
	{
		page= page==null?0:page; size = size==null?Integer.MAX_VALUE:size; 
		Pageable pageable = PageRequest.of(page, size);
		return contactService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public Contact findContactbyvehicleNoContacts(@PathVariable long id) throws Exception 
	{
		return contactService.findSingleContact(id);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteContact(@PathVariable Long id) throws Exception
	{
		
		//contactService.deleteContact(id);
		return ResponseEntity.ok("Entity deleted");
	}
	@PostMapping("/create") 
	@ResponseStatus(HttpStatus.CREATED)
	public Contact createContact(@RequestBody Contact payload) throws Exception
	{
		Contact contact = new Contact();
		try
		{
			contact = contactService.createContact(payload);
		}
		catch(NullPointerException e) 
        { 
            throw new Exception("Some of the required fields are missing"); 
        }
		return contact;
	}

	@PutMapping("/{id}")
	public Contact updateContact(@PathVariable Long id, @RequestBody Contact Contact) throws Exception 
	{
		return contactService.updateContact(id, Contact);
	} 

}
