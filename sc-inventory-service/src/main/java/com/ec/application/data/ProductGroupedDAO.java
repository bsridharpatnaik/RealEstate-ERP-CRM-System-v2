package com.ec.application.data;

import com.ec.application.Deserializers.DoubleTwoDigitDecimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class ProductGroupedDAO 
{
	String productname;
	String measurementUnit;
	@JsonSerialize(using=DoubleTwoDigitDecimalSerializer.class)
	Double quantity;
	public ProductGroupedDAO(String productname, String measurementUnit,Long quantity) {
		super();
		this.productname = productname;
		this.quantity = Double.valueOf(quantity);
		this.measurementUnit=measurementUnit;
	}
	public ProductGroupedDAO(String productname,String measurementUnit, Double quantity) {
		super();
		this.productname = productname;
		this.quantity = Double.valueOf(quantity);
		this.measurementUnit=measurementUnit;
	}
	
	public String getMeasurementUnit() {
		return measurementUnit;
	}
	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
}
