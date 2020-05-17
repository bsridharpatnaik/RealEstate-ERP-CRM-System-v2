package com.ec.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ec.application.data.DashBoardData;
import com.ec.application.data.ReturnAllNotificationsData;
import com.ec.application.service.AllNotificationService;
import com.ec.application.service.DashBoardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController 
{
	@Autowired 
	AllNotificationService allNotificationService;
	
	@Autowired
	DashBoardService dashBoardService;
	
	@GetMapping
	public DashBoardData fetchInventoryDashboard()
	{
		return dashBoardService.getContents();
	}
	
	@GetMapping("/notification")
	public ReturnAllNotificationsData fetchAllNotifications()
	{
		return allNotificationService.getAllNotifications();
	}
	
	@DeleteMapping(value = "/notification/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long id) throws Exception
	{
		allNotificationService.deleteNotification(id);
		return ResponseEntity.ok("Entity deleted");
	}
}
