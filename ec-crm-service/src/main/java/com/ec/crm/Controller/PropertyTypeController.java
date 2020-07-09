package com.ec.crm.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.validation.FieldError;

import com.ec.crm.Data.PropertyTypeListWithTypeAheadData;
import com.ec.crm.Data.SourceListWithTypeAheadData;
import com.ec.crm.Filters.FilterDataList;
import com.ec.crm.Model.PropertyType;
import com.ec.crm.ReusableClasses.IdNameProjections;
import com.ec.crm.Service.PropertyTypeService;

@RestController
@RequestMapping(value="/propertytype",produces = { "application/json", "text/json" })
public class PropertyTypeController {
	@Autowired
	PropertyTypeService propertyService;
	
	@GetMapping
	public Page<PropertyType> returnAllPropertyType(Pageable pageable) 
	{
		return propertyService.fetchAll(pageable);
	}
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public PropertyTypeListWithTypeAheadData returnFilteredSource(@RequestBody FilterDataList sourceFilterDataList,@PageableDefault(page = 0, size = 10, sort = "propertyTypeId", direction = Direction.DESC) Pageable pageable) 
	{
		return propertyService.findFilteredSource(sourceFilterDataList,pageable);
	}
	@GetMapping("/{id}")
	public PropertyType findPropertyTypeByID(@PathVariable long id) throws Exception 
	{
		return propertyService.findSinglePropertyType(id);
	}
	
	@PostMapping("/create") 
	@ResponseStatus(HttpStatus.CREATED)
	public PropertyType createPropertyType(@Valid @RequestBody PropertyType ptype) throws Exception{
		
		return propertyService.createPropertyType(ptype);
	}
	
	@PutMapping("/{id}")
	public PropertyType updatePropertyType(@PathVariable Long id,@Valid @RequestBody PropertyType ptype) throws Exception 
	{
		return propertyService.updatePropertyType(id, ptype);
	} 
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePropertyType(@PathVariable Long id) throws Exception
	{
		propertyService.deletePropertyType(id);
			return ResponseEntity.ok("Source Deleted sucessfully.");
	}
	@GetMapping("/idandnames")
	public List<IdNameProjections> returnIdAndNames() 
	{
		return propertyService.findIdAndNames();
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
