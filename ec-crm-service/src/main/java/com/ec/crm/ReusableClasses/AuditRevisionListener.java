package com.ec.crm.ReusableClasses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.envers.RevisionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ec.crm.Data.UserReturnData;
import com.ec.crm.Service.LeadService;
import com.ec.crm.Service.UserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditRevisionListener implements RevisionListener 
{
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	HttpServletRequest request;
	
	Logger log = LoggerFactory.getLogger(LeadService.class);
	
    @Override
    public void newRevision(Object revisionEntity) 
    {
        AuditRevisionEntity audit = (AuditRevisionEntity) revisionEntity;
        log.info("Fetching user details before submitting revision information");
		UserReturnData userReturnData = userDetailsService.getCurrentUser();
		log.info("Username fetched from common-service - "+userReturnData.getUsername());
        Long userId = userReturnData.getId();
        String userName = userReturnData.getUsername();
        audit.setUserId(userId);
        audit.setUserName(userName);
        log.info("Data set for autit revision");
    }
}
