package com.ec.crm.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.springframework.lang.NonNull;

import com.ec.crm.Enums.ActivityTypeEnum;
import com.ec.crm.ReusableClasses.ReusableFields;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import Deserializers.ToUsernameSerializer;
import lombok.Data;
@Entity
@Table(name = "LeadActivity")
//@Data
@Where(clause = ReusableFields.SOFT_DELETED_CLAUSE) 
public class LeadActivity extends ReusableFields implements Serializable 
{
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "leadactivity_id", updatable = false, nullable = false)
	Long leadActivityId;

	@Column(name="activity_date_time")
	@NonNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
	Date activityDateTime;
	
	@Column(name="title")
	@NonNull
	String title;
	
	@Column(name="duration",columnDefinition = "int default 0")
	Long duration;
	
	@Column(name="description")
	String description;
	
	@ElementCollection
	@CollectionTable(name="lead_activity_tags", joinColumns=@JoinColumn(name="leadActivityId"))
	@Column(name="tags")
	List<String> tags;
	
	@Column(name="isOpen",columnDefinition = "boolean default true")
	@NonNull
	Boolean isOpen;
	
	@JsonSerialize(using=ToUsernameSerializer.class)
	@Column(name="creator_id")
	Long creatorId;
	
	@JsonSerialize(using=ToUsernameSerializer.class)
	@Column(name="closed_by")
	Long closedBy;
	
	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="lead_id",nullable=false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@NotFound(action=NotFoundAction.IGNORE)
	Lead lead;
	
	String closingComment;
	@NonNull
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	ActivityTypeEnum activityType;
	
	public LeadActivity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Long getLeadActivityId() {
		return leadActivityId;
	}


	public void setLeadActivityId(Long leadActivityId) {
		this.leadActivityId = leadActivityId;
	}


	public Date getActivityDateTime() {
		return activityDateTime;
	}


	public void setActivityDateTime(Date activityDateTime) {
		this.activityDateTime = activityDateTime;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Long getDuration() {
		return duration;
	}


	public void setDuration(Long duration) {
		this.duration = duration;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public List<String> getTags() {
		return tags;
	}


	public void setTags(List<String> tags) {
		this.tags = tags;
	}


	public Boolean getIsOpen() {
		return isOpen;
	}


	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}


	public Long getCreatorId() {
		return creatorId;
	}


	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}


	public Long getClosedBy() {
		return closedBy;
	}


	public void setClosedBy(Long closedBy) {
		this.closedBy = closedBy;
	}


	public Lead getLead() {
		return lead;
	}


	public void setLead(Lead lead) {
		this.lead = lead;
	}


	public String getClosingComment() {
		return closingComment;
	}


	public void setClosingComment(String closingComment) {
		this.closingComment = closingComment;
	}


	public ActivityTypeEnum getActivityType() {
		return activityType;
	}


	public void setActivityType(ActivityTypeEnum activityType) {
		this.activityType = activityType;
	}
	
}
