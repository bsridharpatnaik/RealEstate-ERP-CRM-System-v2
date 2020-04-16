package com.ec.application.ReusableClasses;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ec.application.model.Product;
import com.ec.common.Filters.FilterAttributeData;
import com.ec.common.Filters.FilterDataList;

public class SpecificationsBuilder<T>
{

	  //#######################################//
	 //     Level 0   						 //
	//######################################//
	
	
    public Specification<T> whereDirectFieldContains(String key,List<String> names)
    {
    	Specification<T> finalSpec = null;
    	for(String name:names)
    	{
    		Specification<T> internalSpec = (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)
    	            -> cb.like(root.get(key), "%"+ name  +"%");
    	    finalSpec  = specOrCondition(finalSpec,internalSpec);
    	}
        return finalSpec;
    }
    
    public Specification<T> whereDirectFieldEquals(String key,List<String> names)
    {
    	Specification<T> finalSpec = null;
    	for(String name:names)
    	{
    		Specification<T> internalSpec = (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)
    	            -> cb.equal(root.get(key), name);
    	    finalSpec  = specOrCondition(finalSpec,internalSpec);
    	}
        return finalSpec;
    }
    
    
    
	  //#######################################//
	 //     Level 1   						 //
	//######################################//
    
	public Specification<T> whereChildFieldContains(String childTable, String childFiledName,
			List<String> names) 
	{
		Specification<T> finalSpec = null;
    	for(String name:names)
    	{
    		Specification<T> internalSpec = (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)
    	            -> cb.like(root.get(childTable).get(childFiledName), "%"+ name  +"%");
    	    finalSpec  = specOrCondition(finalSpec,internalSpec);
    	}
        return finalSpec;
	}
	
	public Specification<T> whereChildFieldEquals(String childTable, String childFiledName,
			List<String> names) 
	{
		Specification<T> finalSpec = null;
    	for(String name:names)
    	{
    		Specification<T> internalSpec = (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)
    	            -> cb.equal(root.get(childTable).get(childFiledName), name );
    	    finalSpec  = specOrCondition(finalSpec,internalSpec);
    	}
        return finalSpec;
	}
	
	
	  //#######################################//
	 //     Reusable Spec Setter for NULLs    //
	//######################################//
	
    public Specification<T> specAndCondition(Specification<T> finalSpec, Specification<T> internalSpec) 
	{
		if(finalSpec == null) return internalSpec;
		else return finalSpec.and(internalSpec);
	}
    
    public Specification<T> specOrCondition(Specification<T> finalSpec, Specification<T> internalSpec) 
	{
		if(finalSpec == null) return internalSpec;
		else return finalSpec.or(internalSpec);
	}
    
    
	  //############################################//
	 //     Reusable method to fetch filter Data    //
	//############################################//
    
    public static List<String> fetchValueFromFilterList(FilterDataList filterDataList, String field) 
	{
		List<String> returnValue = null;
		for(FilterAttributeData filterData : filterDataList.getFilterData())
		{
			if(filterData.getAttrName().equalsIgnoreCase(field))
				returnValue = filterData.getAttrValue();
		}
		return returnValue;
	}

    
   
}