package com.ec.common.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.common.Model.Role;
import com.ec.common.Repository.RoleRepo;

@Service
public class RoleService 
{

	@Autowired
	RoleRepo roleRepo;
	
	public Page<Role> findAll(Pageable pageable)
	{
		return roleRepo.findAll(pageable);
    }
	
	public Role createRole(Role payload) throws Exception 
	{
		if(roleRepo.countByName(payload.getName())>0)
			throw new Exception("Role already exists!");
		else
			return roleRepo.save(payload);
    }
}
