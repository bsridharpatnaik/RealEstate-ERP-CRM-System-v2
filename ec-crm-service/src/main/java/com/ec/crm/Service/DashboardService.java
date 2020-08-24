package com.ec.crm.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ec.crm.Data.DashboardData;
import com.ec.crm.Model.ConversionRatio;
import com.ec.crm.Model.LeadActivity;
import com.ec.crm.Model.StagnantStats;
import com.ec.crm.Repository.ConvertionRatioRepo;
import com.ec.crm.Repository.LeadActivityRepo;
import com.ec.crm.Repository.StagnantStatsRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DashboardService {
	
	@Autowired
	LeadActivityRepo lRepo;
	
	@Autowired
	StagnantStatsRepo stagnantStatsRepo;
	
	@Autowired
	ConvertionRatioRepo convertionRatioRepo;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	public Map customerpipeline(DashboardData payload) {
		// TODO Auto-generated method stub
		List<LeadActivity> data=new ArrayList<LeadActivity>();
		Date fromdate=payload.getFromDate();
		Date todate=payload.getToDate();
		System.out.println("fromdate : "+fromdate+" todate: "+todate);
		data=lRepo.getActivity(fromdate,todate);

		
		long LeadGenerated = data
				  .stream()
				  .filter(c -> c.getCreatorId() ==404 )
				  .count();
		Map<Long, Long> LeadGeneratedbyAssignee = data
				.stream()
				.filter(c -> c.getCreatorId() ==404 )
			    .collect(Collectors.groupingBy(c -> c.getLead().getAsigneeId(), Collectors.counting()));
		
		long ActivitiesCreated = data
				  .stream()
				  .filter(c -> c.getCreatorId() !=404 )
				  .count();
		Map<Object, Long> ActivitiesCreatedbyAssignee = data
				.stream()
				.filter(c -> c.getCreatorId() !=404 )
			    .collect(Collectors.groupingBy(c -> userDetailsService.getUserFromId(c.getLead().getAsigneeId()).getUsername(), Collectors.counting()));
		
		long TotalPropertyVisit = data
				  .stream()
				  .filter(c -> c.getActivityType().name() == "Property_Visit")
				  .count();
		
		Map<Long, Long> TotalPropertyVisitbyAssignee = data
				.stream()
				.filter(c -> c.getActivityType().name() == "Property_Visit")
			    .collect(Collectors.groupingBy(c -> c.getLead().getAsigneeId(), Collectors.counting()));
		
		
		long DealClosed = data
				  .stream()
				  .filter(c -> c.getActivityType().name() == "Deal_Close")
				  .count();
		
		Map<Long, Long> DealClosedbyAssignee = data
				.stream()
				.filter(c -> c.getActivityType().name() == "Deal_Close")
			    .collect(Collectors.groupingBy(c -> c.getLead().getAsigneeId(), Collectors.counting()));
		
		
		long DealLost = data
				  .stream()
				  .filter(c -> c.getActivityType().name() == "Deal_Lost")
				  .count();
		
		Map<Long, Long> DealLostbyAssignee = data
				.stream()
				.filter(c -> c.getActivityType().name() == "Deal_Lost")
			    .collect(Collectors.groupingBy(c -> c.getLead().getAsigneeId(), Collectors.counting()));
		
		long TotalActivities = data
				  .stream()
				  .filter(c -> c.getIsOpen() == true)
				  .count();
		
		Map<Long, Long> TotalActivitiesbyAssignee = data
				.stream()
				.filter(c -> c.getIsOpen() == true)
			    .collect(Collectors.groupingBy(c -> c.getLead().getAsigneeId(), Collectors.counting()));
		
		
		long PendingActivities = data
				  .stream()
				  .filter(c -> c.getIsOpen() == true)
				  .filter(c -> c.getActivityDateTime().compareTo(new Date())<0)
				  .count();
		
		Map<Long, Long> PendingActivitiesbyAssignee = data
				.stream()
				.filter(c -> c.getIsOpen() == true)
				.filter(c -> c.getActivityDateTime().compareTo(new Date())<0)
			    .collect(Collectors.groupingBy(c -> c.getLead().getAsigneeId(), Collectors.counting()));
		
		
		long UpcomingActivities = data
				  .stream()
				  .filter(c -> c.getIsOpen() == true)
				  .filter(c -> c.getActivityDateTime().compareTo(new Date())>0)
				  .count();
		
		Map<Long, Long> UpcomingActivitiesbyAssignee = data
				.stream()
				.filter(c -> c.getIsOpen() == true)
				.filter(c -> c.getActivityDateTime().compareTo(new Date())>0)
			    .collect(Collectors.groupingBy(c -> c.getLead().getAsigneeId(), Collectors.counting()));
		
		Map returndata=new HashMap<>();
		returndata.put("Lead Generated", LeadGenerated);
		returndata.put("Activities Created", ActivitiesCreated);
		returndata.put("Total Property Visit", TotalPropertyVisit);
		returndata.put("Deal Closed", DealClosed);
		returndata.put("Deal Lost", DealLost);
		returndata.put("Total Activities", TotalActivities);
		returndata.put("Pending Activities", PendingActivities);
		returndata.put("Upcoming Activities", UpcomingActivities);
		returndata.put("Lead Generated by Assignee", LeadGeneratedbyAssignee);
		returndata.put("Activities Created by Assignee", ActivitiesCreatedbyAssignee);
		returndata.put("Total Property Visit by Assignee", TotalPropertyVisitbyAssignee);
		returndata.put("Deal Closed by Assignee", DealClosedbyAssignee);
		returndata.put("Deal Lost by Assignee", DealLostbyAssignee);
		returndata.put("Total Activities by Assignee", TotalActivitiesbyAssignee);
		returndata.put("Pending Activities by Assignee", PendingActivitiesbyAssignee);
		returndata.put("Upcoming Activities by Assignee", UpcomingActivitiesbyAssignee);
		return returndata;
		
	}

	public List<ConversionRatio> conversionratio() 
	{
		return convertionRatioRepo.findAll();
	}

	public  List<StagnantStats> returnStagnantStats() 
	{
		return stagnantStatsRepo.findAll();
	}

	
	 public Map topperformer() 
	 { // TODO Auto-generated method stub
		 List<ConversionRatio> data = convertionRatioRepo.gettopperformer();
		 Map returndata=new HashMap<>();
		 Long id=data.get(0).getUserId();
		 Long propertyvisit=lRepo.getpropertyvisit(id);
		 returndata.put("username", data.get(0).getAsigneeName());
		 returndata.put("lead generated", data.get(0).getTotalcount());
		 returndata.put("property", propertyvisit);
		 returndata.put("deal close", data.get(0).getConvertedcount());
		 return returndata;
	 }
	 

	

}
