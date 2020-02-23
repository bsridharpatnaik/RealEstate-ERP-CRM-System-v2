package com.ec.application.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "Machinery")
@Audited
public class Machinery implements Serializable
{

private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long machineryId;
	
	@NonNull
	@Column(unique=true)
	String machineryName;
	
	String machineryDescription;
	
	public Long getMachineryId() {
		return machineryId;
	}
	public void setMachineryId(Long machineryId) {
		this.machineryId = machineryId;
	}
	
	
	public String getMachineryName() {
		return machineryName;
	}
	public void setMachineryName(String machineryName) {
		this.machineryName = machineryName;
	}
	public String getMachineryDescription() {
		return machineryDescription;
	}
	public void setMachineryDescription(String machineryDescription) {
		this.machineryDescription = machineryDescription;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
