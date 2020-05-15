package com.ec.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ec.application.data.FileUploadSuccessData;
import com.ec.application.model.DBFile;

@Service
public class FileHandlingSgervice 
{
	@Autowired
	DBFileStorageService dbFileStorageService;
	
	public FileUploadSuccessData uploadDoc(MultipartFile file,String type,Long id) throws Exception 
	{
		try
		{
			FileUploadSuccessData fileUploadSuccessData = new FileUploadSuccessData();
			DBFile dbFile = dbFileStorageService.storeFile(file);
			fileUploadSuccessData.setFileid(dbFile.getId());
			fileUploadSuccessData.setFileName(dbFile.getFileName());
			return fileUploadSuccessData;
		}
		catch(Exception e)
		{
			throw new Exception("Error uploading file.");
		}
	}

	public ResponseEntity<Resource> downloadFile(String fileId) throws Exception 
	{
		try
		{
			DBFile dbFile = dbFileStorageService.getFile(fileId);
			return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(dbFile.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
				.body(new ByteArrayResource(dbFile.getData()));
		}	
		catch(Exception e)
		{
			throw new Exception("Error downloading file");
		}
	}

}
