package com.ec.application.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ec.application.data.InwardInventoryData;
import com.ec.application.model.InwardInventory;
import com.ec.application.repository.InwardInventoryRepo;
import com.ec.application.repository.ProductRepo;
import com.ec.application.repository.UnloadingAreaRepo;
import com.ec.application.repository.VendorRepo;

public class InwardInventoryService 
{

	@Autowired
	InwardInventoryRepo inwInvRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	VendorRepo vendorRepo;
	
	@Autowired
	UnloadingAreaRepo unloadingAreaRepo;
	
	public InwardInventory createInwardnventory(InwardInventoryData iiData) throws Exception
	{
		InwardInventory inwardInventory = new InwardInventory();
		validateInputs(iiData);
		setFields(inwardInventory,iiData);
		return inwInvRepo.save(inwardInventory);
		
	}

	private void setFields(InwardInventory inwardInventory, InwardInventoryData iiData) 
	{
		inwardInventory.setDate(iiData.getDate());
		inwardInventory.setOurSlipNo(iiData.getOurSlipNo());
		inwardInventory.setProduct(productRepo.findById(iiData.getProductId()).get());
		inwardInventory.setQuantity(iiData.getQuantity());
		inwardInventory.setUnloadingArea(unloadingAreaRepo.findById(iiData.getUnloadingAreaId()).get());
		inwardInventory.setVehicleNo(iiData.getVehicleNo());
		inwardInventory.setVendor(vendorRepo.findById(iiData.getVendorId()).get());
		inwardInventory.setVendorSlipNo(iiData.getVendorSlipNo());
	}

	private void validateInputs(InwardInventoryData iiData) throws Exception 
	{
		if(iiData.getProductId() == null || iiData.getUnloadingAreaId()==null || iiData.getVendorId()==null)
			throw new Exception("Required field missing");
		
		if(!productRepo.existsById(iiData.getProductId()))
				throw new Exception("Product with ID not found");
		if(!vendorRepo.existsById(iiData.getVendorId()))
			throw new Exception("Vendor with ID not found");
		if(!unloadingAreaRepo.existsById(iiData.getUnloadingAreaId()))
			throw new Exception("Unloading Area with ID not found");
		if(iiData.getQuantity()<=0)
			throw new Exception("Quantity have to be greater the zero");
	}
}
