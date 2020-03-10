package com.ec.application.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ec.application.data.ProductCreateData;
import com.ec.application.model.Category;
import com.ec.application.model.Product;
import com.ec.application.repository.CategoryRepo;
import com.ec.application.repository.ProductRepo;


@Service
public class ProductService 
{

	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	public Page<Product> findAll(Pageable pageable)
	{
		return productRepo.findAll(pageable);
    }
	
	public Product createProduct(ProductCreateData payload) throws Exception 
	{
		if(!productRepo.existsByProductName(payload.getProductName()))
		{
			Optional<Category> categoryOpt = categoryRepo.findById(payload.getCategoryId());
			if(categoryOpt.isPresent())
			{
				Product product = new Product();
				product.setCategory(categoryOpt.get());
				product.setMeasurementUnit(payload.getMeasurementUnit());
				product.setProductDescription(payload.getProductDescription());
				product.setProductName(payload.getProductName());
				productRepo.save(product);
				return product;
			}
			else
			{
				throw new Exception("Category with categoryid not found");
			}
		}
		else
		{
			throw new Exception("Product already exists!");
		}
    }

	public Product updateProduct(Long id, ProductCreateData payload) throws Exception 
	{
		Optional<Product> ProductForUpdateOpt = productRepo.findById(id);
		if(!ProductForUpdateOpt.isPresent())
			throw new Exception("Product not found with productid");
		Optional<Category> categoryOpt = categoryRepo.findById(payload.getCategoryId());
		if(!categoryOpt.isPresent())
			throw new Exception("Category with ID not found");
		
        Product ProductForUpdate = ProductForUpdateOpt.get();
        
        if(!productRepo.existsByProductName(payload.getProductName())
        		&& !payload.getProductName().equalsIgnoreCase(ProductForUpdate.getProductName()))
        {		
        		ProductForUpdate.setProductName(payload.getProductName());
            ProductForUpdate.setProductDescription(payload.getProductDescription());
            ProductForUpdate.setMeasurementUnit(payload.getMeasurementUnit());
            ProductForUpdate.setCategory(categoryOpt.get());
        }
        else if(payload.getProductName().equalsIgnoreCase(ProductForUpdate.getProductName()))
        {
        		ProductForUpdate.setProductDescription(payload.getProductDescription());
        		ProductForUpdate.setMeasurementUnit(payload.getMeasurementUnit());
        		ProductForUpdate.setCategory(categoryOpt.get());
        }
        else 
        {
        	throw new Exception("Product with same Name already exists");
        }
        	
        return productRepo.save(ProductForUpdate);
        
    }

	public Product findSingleProduct(Long id) 
	{
		Optional<Product> Products = productRepo.findById(id);
		return Products.get();
	}
	public void deleteProduct(Long id) throws Exception 
	{
		try
		{
			productRepo.softDeleteById(id);
		}
		catch(Exception e)
		{
			throw new Exception("Not able to delete Product");
		}
	}

	public ArrayList<Product> findProductsByName(String name) 
	{
		ArrayList<Product> productList = new ArrayList<Product>();
		productList = productRepo.findByproductName(name);
		return productList;
	}

	public ArrayList<Product> findProductsByPartialName(String name) 
	{
		return productRepo.findByPartialName(name);
	}

	public ArrayList<String> returnNameByCategory(String categoryname) 
	{
		return productRepo.returnNameByCategory(categoryname);
	}

	public ArrayList<?> findIdAndNames() 
	{
		// TODO Auto-generated method stub
		return productRepo.findIdAndNames();
	}
}
