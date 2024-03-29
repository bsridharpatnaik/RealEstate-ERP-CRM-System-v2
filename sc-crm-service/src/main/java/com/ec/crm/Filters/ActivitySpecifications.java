package com.ec.crm.Filters;

import java.util.List;

import com.ec.crm.Enums.*;
import org.springframework.data.jpa.domain.Specification;

import com.ec.crm.Model.Address_;
import com.ec.crm.Model.Broker_;
import com.ec.crm.Model.LeadActivity;
import com.ec.crm.Model.LeadActivity_;
import com.ec.crm.Model.Lead_;
import com.ec.crm.Model.Source_;
import com.ec.crm.ReusableClasses.ReusableMethods;
import com.ec.crm.ReusableClasses.SpecificationsBuilder;

public class ActivitySpecifications
{
	static SpecificationsBuilder<LeadActivity> specbldr = new SpecificationsBuilder<LeadActivity>();

	public static Specification<LeadActivity> getSpecification(FilterDataList filterDataList) throws Exception
	{
		List<String> name = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "name");
		List<String> mobile = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "mobile");
		List<String> purpose = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "purpose");
		List<String> address = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "address");
		List<String> occupation = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "occupation");
		List<String> broker = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "broker");
		List<String> source = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "source");
		List<String> propertytype = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "propertyType");
		List<String> sentiment = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "sentiment");
		List<String> assignee = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "assignee");
		List<String> createdStartDate = SpecificationsBuilder.fetchValueFromFilterList(filterDataList,
				"createdStartDate");
		List<String> createdEndDate = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "createdEndDate");
		List<String> leadStatus = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "leadStatus");
		List<String> activityType = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "activityType");
		List<String> activityStatus = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "activityStatus");
		List<String> activityStartDate = SpecificationsBuilder.fetchValueFromFilterList(filterDataList,
				"activityStartDate");
		List<String> activityEndDate = SpecificationsBuilder.fetchValueFromFilterList(filterDataList,
				"activityEndDate");
		List<String> globalSearch = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "globalSearch");
		List<String> stagnantStatus = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "stagnantStatus");
		List<String> loanStatus = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "loanStatus");
		List<String> customerStatus = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "customerStatus");
		List<String> dealLostReason = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "dealLostReason");

		List<String> showOnlyLatest = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "showOnlyLatest");
		List<String> prospectLeads = SpecificationsBuilder.fetchValueFromFilterList(filterDataList, "prospectLead");
		List<String> showRescheduled = SpecificationsBuilder.fetchValueFromFilterList(filterDataList,
				"showRescheduled");
		Specification<LeadActivity> finalSpec = null;

		if (name != null && name.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereChildFieldContains(LeadActivity_.LEAD, Lead_.customerName.getName(), name));

		if (loanStatus != null && loanStatus.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereChildFieldContains(LeadActivity_.LEAD, Lead_.loanStatus.getName(), loanStatus));

		if (prospectLeads != null && prospectLeads.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereChildBoleanFieldEquals(LeadActivity_.LEAD, Lead_.IS_PROSPECT_LEAD, prospectLeads));

		if (customerStatus != null && customerStatus.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereChildFieldContains(LeadActivity_.LEAD, Lead_.customerStatus.getName(), customerStatus));

		if (mobile != null && mobile.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereChildFieldContains(LeadActivity_.LEAD, Lead_.PRIMARY_MOBILE, mobile)
							.or(specbldr.whereChildFieldContains(LeadActivity_.LEAD, Lead_.SECONDARY_MOBILE, mobile)));

		if (purpose != null && purpose.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereChildFieldContains(LeadActivity_.LEAD, Lead_.PURPOSE, purpose));

		if (address != null && address.size() > 0)
		{
			Specification<LeadActivity> addressSpec = specbldr
					.whereGrandChildFieldContains(LeadActivity_.LEAD, Lead_.ADDRESS, Address_.ADDR_LINE1, address)
					.or(specbldr.whereGrandChildFieldContains(LeadActivity_.LEAD, Lead_.ADDRESS, Address_.ADDR_LINE2,
							address))
					.or(specbldr.whereGrandChildFieldContains(LeadActivity_.LEAD, Lead_.ADDRESS, Address_.CITY,
							address))
					.or(specbldr.whereGrandChildFieldContains(LeadActivity_.LEAD, Lead_.ADDRESS, Address_.PINCODE,
							address));
			finalSpec = specbldr.specAndCondition(finalSpec, addressSpec);

		}

		if (globalSearch != null && globalSearch.size() > 0)
		{
			Specification<LeadActivity> globalSearchSpec = specbldr
					.whereChildFieldContains(LeadActivity_.LEAD, Lead_.CUSTOMER_NAME, globalSearch)
					.or(specbldr.whereChildFieldContains(LeadActivity_.LEAD, Lead_.PRIMARY_MOBILE, globalSearch));

			finalSpec = specbldr.specAndCondition(finalSpec, globalSearchSpec);

		}

		if (occupation != null && occupation.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereChildFieldContains(LeadActivity_.LEAD, Lead_.OCCUPATION, occupation));

		if (broker != null && broker.size() > 0)
		{
			if (ReusableMethods.isNumeric(broker.get(0)))
				finalSpec = specbldr.specAndCondition(finalSpec, specbldr
						.whereGrandChildLongFieldContains(LeadActivity_.LEAD, Lead_.BROKER, Broker_.BROKER_ID, broker));
			else
				finalSpec = specbldr.specAndCondition(finalSpec, specbldr
						.whereGrandChildFieldContains(LeadActivity_.LEAD, Lead_.BROKER, Broker_.BROKER_NAME, broker));
		}
		if (broker != null && broker.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereGrandChildFieldContains(LeadActivity_.LEAD, Lead_.BROKER, Broker_.BROKER_NAME, broker)
							.or(specbldr.whereGrandChildLongFieldContains(LeadActivity_.LEAD, Lead_.BROKER,
									Broker_.BROKER_ID, broker)));

		if (source != null && source.size() > 0)
		{
			if (ReusableMethods.isNumeric(source.get(0)))
			{
				finalSpec = specbldr.specAndCondition(finalSpec, specbldr
						.whereGrandChildLongFieldContains(LeadActivity_.LEAD, Lead_.SOURCE, Source_.SOURCE_ID, source));
			} else
			{
				finalSpec = specbldr.specAndCondition(finalSpec, specbldr
						.whereGrandChildFieldContains(LeadActivity_.LEAD, Lead_.SOURCE, Source_.SOURCE_NAME, source));
			}
		}
		if (propertytype != null && propertytype.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec, specbldr.whereChildEnumFieldEquals(LeadActivity_.LEAD,
					Lead_.PROPERTY_TYPE, propertytype, PropertyTypeEnum.class));

		if (sentiment != null && sentiment.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec, specbldr.whereChildEnumFieldEquals(LeadActivity_.LEAD,
					Lead_.SENTIMENT, sentiment, SentimentEnum.class));

		if (assignee != null && assignee.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereChildAssigneeContains(LeadActivity_.LEAD, Lead_.ASIGNEE_ID, assignee));

		if (createdStartDate != null && createdStartDate.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereChildFieldDateGreaterThan(LeadActivity_.LEAD, Lead_.CREATED, createdStartDate));

		if (createdEndDate != null && createdEndDate.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereChildFieldDateLessThan(LeadActivity_.LEAD, Lead_.CREATED, createdEndDate));

		if (leadStatus != null && leadStatus.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec, specbldr.whereChildEnumFieldEquals(LeadActivity_.LEAD,
					Lead_.STATUS, leadStatus, LeadStatusEnum.class));

		if (dealLostReason != null && dealLostReason.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec, specbldr.whereEnumFieldEquals(LeadActivity_.dealLostReason.getName(),
					dealLostReason, DealLostReasonEnum.class));

		if (activityType != null && activityType.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereEnumFieldEquals(LeadActivity_.ACTIVITY_TYPE, activityType, ActivityTypeEnum.class));

		if (activityStatus != null && activityStatus.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereDirectBoleanFieldEquals(LeadActivity_.IS_OPEN, activityStatus));

		if (activityStartDate != null && activityStartDate.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec, specbldr
					.whereDirectFieldDateGreaterThanOrEqual(LeadActivity_.ACTIVITY_DATE_TIME, activityStartDate));

		if (activityEndDate != null && activityEndDate.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereDirectFieldDateLessThanOrEqual(LeadActivity_.ACTIVITY_DATE_TIME, activityEndDate));

		if (showOnlyLatest != null && showOnlyLatest.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereDirectIntFieldEquals(LeadActivity_.IS_LATEST, showOnlyLatest));

		if (showRescheduled != null && showRescheduled.size() > 0)
			finalSpec = specbldr.specAndCondition(finalSpec,
					specbldr.whereDirectIntFieldEquals(LeadActivity_.IS_RESCHEDULED, showRescheduled));

		if (stagnantStatus != null && stagnantStatus.size() > 0)
		{
			Specification<LeadActivity> internalSpec = null;
			for (String str : stagnantStatus)
			{
				if (str.equalsIgnoreCase("NoColour"))
					internalSpec = specbldr.specOrCondition(internalSpec,
							specbldr.whereChildFieldIntBetween(LeadActivity_.LEAD, Lead_.STAGNANT_DAYS_COUNT, 0, 9));
				if (str.equalsIgnoreCase("Green"))
					internalSpec = specbldr.specOrCondition(internalSpec,
							specbldr.whereChildFieldIntBetween(LeadActivity_.LEAD, Lead_.STAGNANT_DAYS_COUNT, 10, 19));
				if (str.equalsIgnoreCase("Orange"))
					internalSpec = specbldr.specOrCondition(internalSpec,
							specbldr.whereChildFieldIntBetween(LeadActivity_.LEAD, Lead_.STAGNANT_DAYS_COUNT, 20, 29));
				if (str.equalsIgnoreCase("Red"))
					internalSpec = specbldr.specOrCondition(internalSpec, specbldr.whereChildFieldIntBetween(
							LeadActivity_.LEAD, Lead_.STAGNANT_DAYS_COUNT, 30, Integer.MAX_VALUE));
			}
			finalSpec = specbldr.specAndCondition(finalSpec, internalSpec);
		}
		return finalSpec;
	}
}
