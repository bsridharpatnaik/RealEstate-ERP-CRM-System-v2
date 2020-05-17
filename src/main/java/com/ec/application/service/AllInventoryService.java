package com.ec.application.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ec.application.data.AllInventoryReturnData;
import com.ec.application.data.DashboardInwardOutwardInventoryDAO;
import com.ec.application.model.AllInventoryTransactions;
import com.ec.application.repository.AllInventoryRepo;
import com.ec.common.Filters.AllInventorySpecification;
import com.ec.common.Filters.FilterDataList;

@Service
public class AllInventoryService 
{
	@Autowired
	AllInventoryRepo allInventoryRepo;
	
	@Autowired
	PopulateDropdownService populateDropdownService;
	
	
	public AllInventoryReturnData fetchAllInventory(FilterDataList filterDataList, Pageable pageable) throws ParseException 
	{
		AllInventoryReturnData allInventoryReturnData = new AllInventoryReturnData();
		Specification<AllInventoryTransactions> spec = AllInventorySpecification.getSpecification(filterDataList);
		
		if(spec!=null)
			allInventoryReturnData.setTransactions(allInventoryRepo.findAll(spec,pageable));
		else
			allInventoryReturnData.setTransactions(allInventoryRepo.findAll(pageable));
		allInventoryReturnData.setLdDropdown(populateDropdownService.fetchData("allinventory"));
		return allInventoryReturnData;
	}
	
	/*public List<DashboardInwardOutwardInventoryDAO> fetchOutwardForDashboard() 
	{
		List<DashboardInwardOutwardInventoryDAO> outwardList = new ArrayList<DashboardInwardOutwardInventoryDAO>();
		outwardList = allInventoryRepo.findForDashboard();
		return outwardList;
	}*/

	public List<DashboardInwardOutwardInventoryDAO> fetchInventoryForDashboard(String type) 
	{
		Pageable pageable = PageRequest.of(0, 5,Sort.by("created").descending());
		return allInventoryRepo.findForDashboard(type, pageable);
	}
}
