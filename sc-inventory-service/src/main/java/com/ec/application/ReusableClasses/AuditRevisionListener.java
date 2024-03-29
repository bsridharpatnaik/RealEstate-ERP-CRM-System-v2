package com.ec.application.ReusableClasses;

import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.ec.application.data.UserReturnData;
import com.ec.application.service.UserDetailsService;

public class AuditRevisionListener implements RevisionListener
{
	@Autowired
	UserDetailsService userDetailsService;

	@Override
	public void newRevision(Object revisionEntity)
	{
		AuditRevisionEntity audit = (AuditRevisionEntity) revisionEntity;
		UserReturnData userReturnData;
		try
		{
			userReturnData = userDetailsService.getCurrentUser();
			Long userId = userReturnData.getId();
			String userName = userReturnData.getUsername();
			audit.setUserId(userId);
			audit.setUserName(userName);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
