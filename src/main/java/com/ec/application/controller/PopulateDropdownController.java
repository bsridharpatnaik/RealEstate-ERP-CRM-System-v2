package com.ec.application.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ec.application.data.MORDropdownData;
import com.ec.application.model.Product;
import com.ec.application.service.PopulateDropdownService;

@RestController
@RequestMapping("ec/dropdown")
public class PopulateDropdownController 
{

	@Autowired
	PopulateDropdownService pdService;
	
	@GetMapping("/mor")
	public ArrayList<MORDropdownData> findProductbyvehicleNoProducts() 
	{
		return pdService.fetchData();
	}
}
