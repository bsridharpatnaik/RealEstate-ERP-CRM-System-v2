package com.ec.crm.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ec.crm.Enums.ActivityTypeEnum;
import com.ec.crm.Enums.LeadStatusEnum;
import com.ec.crm.Model.LeadActivity;
import com.ec.crm.Service.LeadActivityService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import Deserializers.ToUsernameSerializer;
import lombok.Data;

@Data
@NoArgsConstructor
public class LeadPageData 
{
	Long leadId;
	
	String name;
	
	String mobileNumber;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm")
	Date activityDateTime;

	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	ActivityTypeEnum activityType;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	LeadStatusEnum leadStatus;
	
	Boolean isOpen;
	
	@JsonSerialize(using=ToUsernameSerializer.class)
	Long assigneeId;

	Integer followUpCount;

	public void LeadPageData(LeadActivity la)
	{
		this.leadId = la.getLead().getLeadId();
		this.name = la.getLead().getCustomerName();
		this.mobileNumber = la.getLead().getPrimaryMobile();
		this.activityType=la.getActivityType();
		this.activityDateTime=la.getActivityDateTime();
		this.leadStatus=la.getLead().getStatus();
		this.isOpen=la.getIsOpen();
		this.assigneeId = la.getLead().getAsigneeId();
		this.followUpCount = la.getFollowUpCount()==null?null:la.getFollowUpCount();
	}
}
