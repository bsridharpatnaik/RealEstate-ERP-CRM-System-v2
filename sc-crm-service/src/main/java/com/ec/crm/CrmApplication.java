package com.ec.crm;

import java.util.Map;

import com.ec.crm.Enums.InstanceEnum;
import com.ec.crm.Service.InstanceService;
import com.ec.crm.Service.SchedulerService;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.ec.crm.Data.LeadDAO;
import com.ec.crm.Data.LeadPageData;
import com.ec.crm.Model.Lead;
import com.ec.crm.Model.LeadActivity;
import com.ec.crm.Service.LeadActivityService;
import com.ec.crm.Service.UserDetailsService;

@SpringBootApplication
public class CrmApplication extends SpringBootServletInitializer
{
	@Autowired
	LeadActivityService leadActivityService;

	@Autowired
	UserDetailsService udService;

	public static void main(String[] args)
	{
		SpringApplication.run(CrmApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		// The Scheduler Class needs to be added manually, because it dont have to be defined as Bean
		return application.sources(CrmApplication.class, SchedulerService.class);
	}

	@Bean
	public ModelMapper leadToLeadActivityModelMapper()
	{
		ModelMapper modelMapper = new ModelMapper();
		Converter<LeadActivity, LeadPageData> leadActivity = new AbstractConverter<LeadActivity, LeadPageData>()
		{
			@Override
			protected LeadPageData convert(LeadActivity la)
			{
				// LeadActivity leadActivity =
				// leadActivityService.getRecentActivityByLeadId(lead.getLeadId());
				LeadPageData leadPageData = new LeadPageData();
				leadPageData.setAssigneeId(la.getLead().getAsigneeId());
				leadPageData.setLeadId(la.getLead().getLeadId());
				leadPageData.setLeadStatus(la.getLead().getStatus());
				leadPageData.setMobileNumber(la.getLead().getPrimaryMobile());
				leadPageData.setName(la.getLead().getCustomerName());
				leadPageData.setActivityDateTime(la.getActivityDateTime());
				leadPageData.setActivityType(la.getActivityType());
				leadPageData.setIsOpen(la.getIsOpen());
				return leadPageData;
			}
		};

		modelMapper.addConverter(leadActivity);
		return modelMapper;
	}

	@Bean
	public ModelMapper leadToLeadDAOModelMapper()
	{
		ModelMapper modelMapper = new ModelMapper();
		Converter<Lead, LeadDAO> leadActivity = new AbstractConverter<Lead, LeadDAO>()
		{
			@Override
			protected LeadDAO convert(Lead l)
			{
				// LeadActivity leadActivity =
				// leadActivityService.getRecentActivityByLeadId(lead.getLeadId());
				LeadDAO leadDAO = new LeadDAO();
				leadDAO.setSentiment(l.getSentiment());
				leadDAO.setAddr_line1(l.getAddress().getAddr_line1());
				leadDAO.setAddr_line2(l.getAddress().getAddr_line2());
				leadDAO.setCity(l.getAddress().getCity());
				leadDAO.setPincode(l.getAddress().getPincode());
				leadDAO.setAsigneeId(l.getAsigneeId());
				leadDAO.setBroker(l.getBroker() == null ? "" : l.getBroker().getBrokerName());
				leadDAO.setCreatorId(l.getCreatorId());
				leadDAO.setCustomerName(l.getCustomerName());
				leadDAO.setDateOfBirth(l.getDateOfBirth());
				leadDAO.setEmailId(l.getEmailId());
				leadDAO.setLeadId(l.getLeadId());
				leadDAO.setOccupation(l.getOccupation());
				leadDAO.setPrimaryMobile(l.getPrimaryMobile());
				leadDAO.setPropertyType(l.getPropertyType());
				leadDAO.setPurpose(l.getPurpose());
				leadDAO.setSecondaryMobile(l.getSecondaryMobile());
				leadDAO.setSource(l.getSource() == null ? "" : l.getSource().getSourceName());
				leadDAO.setStatus(l.getStatus());
				leadDAO.setAssigneeUserId(l.getAsigneeId());
				leadDAO.setStagnantDaysCount(l.getStagnantDaysCount());
				leadDAO.setLastActivityModifiedDate(l.getLastActivityModifiedDate());
				return leadDAO;
			}
		};

		modelMapper.addConverter(leadActivity);
		return modelMapper;
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Bean("userIdNameMap")
	public Map<Long, String> initializeUserIdNameMap() throws Exception {
		return udService.fetchUserListAsMap();
	}
}
