package com.ec.crm.Data;

import java.util.List;

import org.springframework.lang.NonNull;

import com.ec.crm.Model.Broker;

import lombok.Data;

@Data
public class NoteCreateData {
	
	String content;
	
	@NonNull
	Long leadId;
	
	Boolean pinned;
	
	@NonNull
	List<FileInformationDAO> fileInformations;
	
	@Override
	public String toString() {
		return "NoteCreateData [content=" + content + ", leadId=" + leadId + ", pinned=" + pinned
				+ ", fileInformations=" + fileInformations + "]";
	}
}
