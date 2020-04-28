package com.ec.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ec.application.data.OutwardInventoryCreateData;
import com.ec.application.data.ReturnOutwardInventoryData;
import com.ec.application.model.OutwardInventory;
import com.ec.application.service.OutwardInventoryService;
import com.ec.common.Filters.FilterDataList;

@RestController
@RequestMapping("/outward")
public class OutwardInventoryController 
{
	@Autowired
	OutwardInventoryService oiService ;
	
	
	
	@PostMapping("/create") 
	@ResponseStatus(HttpStatus.CREATED)
	public OutwardInventory createOutwardInventory(@RequestBody OutwardInventoryCreateData payload) throws Exception
	{
		return oiService.createOutwardnventory(payload);
	}
	
	@GetMapping("/{id}")
	public OutwardInventory getOutwardInventory(@PathVariable Long id) throws Exception
	{
		
		return oiService.findOutwardnventory(id);
	}
	
	@PostMapping 
	@ResponseStatus(HttpStatus.OK)
	public ReturnOutwardInventoryData fetchAllOutwardInventory(@RequestBody FilterDataList filterDataList,@RequestParam(name="page",required = false) Integer page,@RequestParam(name="size",required = false) Integer size) throws Exception
	{
		page= page==null?0:page; size = size==null?Integer.MAX_VALUE:size; 
		Pageable pageable = PageRequest.of(page, size);
		return oiService.fetchOutwardnventory(filterDataList,pageable);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteOutwardInventoryById(@PathVariable Long id) throws Exception
	{
		
		oiService.deleteOutwardInventoryById(id);
		return ResponseEntity.ok("Entity deleted");
	}
	/*
	
	@PutMapping("/{id}")
	public OutwardInventory updateOutwardInventory(@RequestBody OutwardInventoryCreateData payload,@PathVariable Long id) throws Exception
	{
		
		return outwardInventoryService.updateOutwardnventory(payload, id);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteOutwardInventory(@PathVariable Long id) throws Exception
	{
		outwardInventoryService.deleteOutwardnventory(id);
		return ResponseEntity.ok("Entity deleted");
	}
	*/
}
