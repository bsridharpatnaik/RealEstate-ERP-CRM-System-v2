package com.ec.common.Controller;

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

import com.ec.common.Data.ContactsWithTypeAhead;
import com.ec.common.Filters.FilterDataList;
import com.ec.common.Model.ContactAllInfo;
import com.ec.common.Service.ContactService;

@RestController
@RequestMapping(value="/contact",produces = { "application/json", "text/json" })
public class ContactController 
{

	@Autowired
	ContactService contactService;
	
	@GetMapping
	public ContactsWithTypeAhead returnAllContacts(@RequestParam(name="page",required = false) Integer page,@RequestParam(name="size",required = false) Integer size) 
	{
		page= page==null?0:page; size = size==null?Integer.MAX_VALUE:size; 
		Pageable pageable = PageRequest.of(page, size);
		return contactService.findAllWithTypeAhead(pageable);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public ContactsWithTypeAhead returnFilteredContacts(@RequestBody FilterDataList contactFilterDataList,@RequestParam(name="page",required = false) Integer page,@RequestParam(name="size",required = false) Integer size) 
	{
		page= page==null?0:page; size = size==null?Integer.MAX_VALUE:size; 
		Pageable pageable = PageRequest.of(page, size);
		return contactService.findFilteredContactsWithTA(contactFilterDataList,pageable);
	}
	
	@GetMapping("/{id}")
	public ContactAllInfo findContactbyvehicleNoContacts(@PathVariable long id) throws Exception 
	{
		return contactService.findSingleContactFromAll(id);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteContact(@PathVariable Long id) throws Exception
	{
		//contactService.deleteContact(id);
		return ResponseEntity.ok("Cannot Delete Enitity. Contact Administrator.");
	}
	@PostMapping("/create") 
	@ResponseStatus(HttpStatus.CREATED)
	public ContactAllInfo createContact(@RequestBody ContactAllInfo payload) throws Exception
	{
		return(contactService.createContact(payload));
	}

	@PutMapping("/{id}")
	public ContactAllInfo updateContact(@PathVariable Long id, @RequestBody ContactAllInfo payload) throws Exception 
	{
		return contactService.updateContact(id, payload);
	} 
}
