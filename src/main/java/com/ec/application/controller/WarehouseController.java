package com.ec.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ec.application.ReusableClasses.IdNameProjections;
import com.ec.application.model.Warehouse;
import com.ec.application.service.WarehouseService;

@RestController
@RequestMapping(value="/warehouse",produces = { "application/json", "text/json" })
public class WarehouseController 
{
	@Autowired
	WarehouseService warehouseService;
	
	@GetMapping
	public Page<Warehouse> returnAllWarehouses(Pageable pageable) 
	{
		
		return warehouseService.findAll(pageable);
	}
	
	@PostMapping("/create") 
	@ResponseStatus(HttpStatus.CREATED)
	public Warehouse createWarehouse(@RequestBody Warehouse payload) throws Exception{
		
		return warehouseService.createWarehouse(payload);
	}

	@PutMapping("/{id}") 
	@ResponseStatus(HttpStatus.OK)
	public Warehouse updateWarehouses(@PathVariable Long id,@RequestBody Warehouse payload) throws Exception{
		
		return warehouseService.updateWarehouse(id, payload);
	}
	
	@GetMapping("/idandnames")
	public List<IdNameProjections> returnIdandNames() 
	{
		return warehouseService.findIdAndNames();
	}
}
