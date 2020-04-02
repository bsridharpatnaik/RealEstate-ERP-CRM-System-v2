package com.ec.gateway.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ec.gateway.Data.CreateUserData;
import com.ec.gateway.Data.ResetPasswordData;
import com.ec.gateway.Data.UpdateRolesForUserData;
import com.ec.gateway.Data.UserReturnData;
import com.ec.gateway.Data.UsersWithRoleNameListData;
import com.ec.gateway.Model.Role;
import com.ec.gateway.Model.User;
import com.ec.gateway.Repository.RoleRepo;
import com.ec.gateway.Repository.UserRepo;

@Service
public class UserService 
{
	@Autowired
	UserRepo uRepo;

	@Autowired
	RoleRepo rRepo;
	
	public User createUser(CreateUserData userData) throws Exception
	{		
		String username = userData.getUsername();
		String password = userData.getPassword();
		ArrayList<String> roles = userData.getRole(); 
	
		if(uRepo.findUserByUsername(username).size()==0)
		{
			User user = new User();
			Set<Role> roleset = new HashSet<Role>();
			
			for(String role : roles)
			{
				Role roleEntity = rRepo.findByName(role);
				roleset.add(roleEntity);
			}
				user.setUserName(username);
				user.setStatus(true);
				user.setRoles(roleset);
				user.setPassword(bCryptPassword(password));
				user.setPasswordExpired(false);
				uRepo.save(user);
				return user;
	
		}
		else
		{
			throw new Exception("User already exists");
		}
	}

	public String bCryptPassword(String password)
	{
		String bcyptedPassword;
		bcyptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		return bcyptedPassword;
	}

	public UsersWithRoleNameListData fetchAll(Pageable pageable)
	{
		UsersWithRoleNameListData usersWithRoleNameListData = new UsersWithRoleNameListData();
		usersWithRoleNameListData.setUsers(uRepo.findAll(pageable));
		usersWithRoleNameListData.setRoles(rRepo.findRoleNames());
		return usersWithRoleNameListData;
	}
	
	public User resetPassword(ResetPasswordData rpData) throws Exception 
	{
		String username = rpData.getUsername();
		String password = rpData.getNewPassword();
		ArrayList<User> users = uRepo.findUserByUsername(username);
		if(users.size()==1)
		{
			User user = users.get(0);
			user.setPassword(bCryptPassword(password));
			uRepo.save(user);
			return user;
		}
		else 
		{
			throw new Exception ("No or Multiple users found by username!");
		}
	}
	public User updateRolesForUser(UpdateRolesForUserData upRoleData) throws Exception 
	{
		String username = upRoleData.getUsername();
		ArrayList<String> roles = upRoleData.getRoles();
		ArrayList<User> users = uRepo.findUserByUsername(username);
		if(users.size()==1)
		{
			User user = users.get(0);
			Set<Role> roleset = new HashSet<Role>();
			
			for(String role : roles)
			{
				Role roleEntity = rRepo.findByName(role);
				if(roleEntity!=null)
				roleset.add(roleEntity);
			}
			user.setRoles(roleset);
			uRepo.save(user);
			return user;
		}
		else 
		{
			throw new Exception ("No or Multiple users found by username!");
		}
	}

	public UserReturnData fetchUserDetails() 
	{
		UserReturnData userReturnData = new UserReturnData();
		List<String> roles = new ArrayList<String>();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		userReturnData.setUsername(auth.getName());
		
		
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for(SimpleGrantedAuthority authority : authorities)
		{
			roles.add(authority.getAuthority());
		}
		userReturnData.setRoles(roles);
		return userReturnData;
	}
	}
