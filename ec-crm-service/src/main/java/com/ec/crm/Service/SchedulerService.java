package com.ec.crm.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulerService
{

	@Autowired
	SendCRMNotificationsService sendCRMNotificationsService;

	Logger log = LoggerFactory.getLogger(SchedulerService.class);

	@Scheduled(cron = "* * * * * *")
	public void sendStockNotificationEmailInEvening() throws Exception
	{
		sendCRMNotificationsService.sendNotificationForUpcomingActivities();
	}
}
