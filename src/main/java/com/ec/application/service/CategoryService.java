package com.ec.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ec.application.ReusableClasses.IdNameProjections;
import com.ec.application.data.AllCategoriesWithNamesData;
import com.ec.application.model.Category;
import com.ec.application.repository.CategoryRepo;
import com.ec.application.repository.ProductRepo;
import com.ec.common.Filters.CategorySpecifications;
import com.ec.common.Filters.FilterAttributeData;
import com.ec.common.Filters.FilterDataList;


@Service
public class CategoryService 
{

	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	ProductRepo pRepo;
	
	@Autowired
	CheckBeforeDeleteService checkBeforeDeleteService;
	
	public Page<Category> findAll(Pageable pageable)
	{
		return categoryRepo.findAll(pageable);
    }
	
	public Category createCategory(Category payload) throws Exception 
	{
		if(!categoryRepo.existsByCategoryName(payload.getCategoryName()))
		{
			categoryRepo.save(payload);
			return payload;
		}
		else
		{
			throw new Exception("Category already exists!");
		}
    }

	public Category updateCategory(Long id, Category payload) throws Exception 
	{
		Optional<Category> CategoryForUpdateOpt = categoryRepo.findById(id);
        Category CategoryForUpdate = CategoryForUpdateOpt.get();
        
		Category newCategory = new Category();
        newCategory = payload;
        if(!categoryRepo.existsByCategoryName(newCategory.getCategoryName()) && 
        		!newCategory.getCategoryName().equalsIgnoreCase(CategoryForUpdate.getCategoryName()))
        {		
        	CategoryForUpdate.setCategoryName(newCategory.getCategoryName());
            CategoryForUpdate.setCategoryDescription(newCategory.getCategoryDescription());
        }
        else if(newCategory.getCategoryName().equalsIgnoreCase(CategoryForUpdate.getCategoryName()))
        {
        	CategoryForUpdate.setCategoryDescription(newCategory.getCategoryDescription());
        }
        else 
        {
        	throw new Exception("Category with same Name already exists");
        }
        	
        return categoryRepo.save(CategoryForUpdate);
        
    }

	public Category findSingleCategory(Long id) 
	{
		Optional<Category> Categorys = categoryRepo.findById(id);
		return Categorys.get();
	}
	public void deleteCategory(Long id) throws Exception 
	{
			if(!checkBeforeDeleteService.isCategoryUsed(id))
				categoryRepo.softDeleteById(id);
			else
				throw new Exception("Cannot delete category. Category already assigned to product");
	}

	public ArrayList<Category> findCategorysByName(String name) 
	{
		ArrayList<Category> categorytList = new ArrayList<Category>();
		categorytList = categoryRepo.findBycategoryName(name);
		return categorytList;
	}

	public ArrayList<Category> findCategorysByPartialName(String name) 
	{
		return categoryRepo.findByPartialName(name);
	}

	public List<IdNameProjections> findIdAndNames() 
	{
		// TODO Auto-generated method stub
		return categoryRepo.findIdAndNames();
	}

	public AllCategoriesWithNamesData findFilteredCategoriesWithTA(FilterDataList filterDataList, Pageable pageable) 
	{
		AllCategoriesWithNamesData allCategoriesWithNamesData = new AllCategoriesWithNamesData();
		Specification<Category> spec = fetchSpecification(filterDataList);
		
		if(spec!=null) allCategoriesWithNamesData.setCategories(categoryRepo.findAll(spec, pageable));
		else allCategoriesWithNamesData.setCategories(categoryRepo.findAll(pageable));

		allCategoriesWithNamesData.setNames(categoryRepo.getNames());
		return allCategoriesWithNamesData;
		
	}

	private Specification<Category> fetchSpecification(FilterDataList filterDataList) 
	{
		Specification<Category> specification = null;
		for(FilterAttributeData attrData:filterDataList.getFilterData())
		{
			String attrName = attrData.getAttrName();
			List<String> attrValues = attrData.getAttrValue();
			
			if(attrName.toUpperCase().equals("NAME"))
			{
				Specification<Category> internalSpecification = null;
				for(String attrValue : attrValues)
				{
					internalSpecification= internalSpecification==null?
							CategorySpecifications.whereCategoryNameContains(attrValue)
							:internalSpecification.or(CategorySpecifications.whereCategoryNameContains(attrValue));
				}
				specification= specification==null?internalSpecification:specification.and(internalSpecification);
			}
		}
		return specification;
	}
}
