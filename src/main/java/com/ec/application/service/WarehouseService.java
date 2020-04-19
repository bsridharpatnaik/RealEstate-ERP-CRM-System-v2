package com.ec.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.application.ReusableClasses.IdNameProjections;
import com.ec.application.model.Warehouse;
import com.ec.application.repository.WarehouseRepo;

@Service
public class WarehouseService 
{
	@Autowired
	WarehouseRepo warehouseRepo;
	
	public Page<Warehouse> findAll(Pageable pageable)
	{
		return warehouseRepo.findAll(pageable);
    }
	
	public Warehouse createWarehouse(Warehouse payload) throws Exception 
	{
		if(warehouseRepo.countByName(payload.getWarehouseName())>0)
			throw new Exception("Warehouse already exists!");
		else
			return warehouseRepo.save(payload);
    }
	
	public Warehouse updateWarehouse(Long id,Warehouse payload) throws Exception 
	{
		Optional<Warehouse> warehouseOpt = warehouseRepo.findById(id);
		if(!warehouseOpt.isPresent())
			throw new Exception("Warehouse with name not found");
		
		Warehouse warehouse = warehouseOpt.get();
		
		if(warehouse.getWarehouseName() != payload.getWarehouseName() && warehouseRepo.countByName(payload.getWarehouseName())>0)
			throw new Exception("Warehouse already exists!");
		else
			return warehouseRepo.save(payload);
    }
	
	public List<IdNameProjections> findIdAndNames() 
	{
		// TODO Auto-generated method stub
		return warehouseRepo.findIdAndNames();
	} 
}
