package com.ec.crm.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ec.crm.Model.Status;
import com.ec.crm.ReusableClasses.IdNameProjections;
import com.ec.crm.Service.StatusService;

@RestController
@RequestMapping(value="/status",produces = { "application/json", "text/json" })
public class StatusController {
	@Autowired
	StatusService statusService;
	
	@GetMapping
	public Page<Status> returnAllStatus(Pageable pageable) 
	{
		return statusService.fetchAll(pageable);
	}
	
	@GetMapping("/{id}")
	public Status findStatusByID(@PathVariable long id) throws Exception 
	{
		return statusService.findSingleStatus(id);
	}
	
	@PostMapping("/create") 
	@ResponseStatus(HttpStatus.CREATED)
	public Status createStatus(@Valid @RequestBody Status status) throws Exception{
		
		return statusService.createStatus(status);
	}
	
	@PutMapping("/{id}")
	public Status updateStatus(@PathVariable Long id,@Valid @RequestBody Status status) throws Exception 
	{
		return statusService.updateStatus(id, status);
	} 
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStatus(@PathVariable Long id) throws Exception
	{
		statusService.deleteStatus(id);
			return ResponseEntity.ok("Status Deleted sucessfully.");
	}
	@GetMapping("/idandnames")
	public List<IdNameProjections> returnIdAndNames() 
	{
		return statusService.findIdAndNames();
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
