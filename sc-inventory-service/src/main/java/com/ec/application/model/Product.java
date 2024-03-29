package com.ec.application.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.springframework.lang.NonNull;

import com.ec.application.ReusableClasses.ReusableFields;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Product")
@Audited
@Where(clause = ReusableFields.SOFT_DELETED_CLAUSE)
public class Product extends ReusableFields
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long productId;

	@NonNull
	@Column(name = "product_name")
	String productName;

	String productDescription;

	String measurementUnit;

	Double reorderQuantity;

	@ManyToOne(fetch = FetchType.EAGER, cascade =
	{ CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "categoryId", nullable = false)
	@JsonIgnoreProperties(
	{ "hibernateLazyInitializer", "handler" })
	@NotFound(action = NotFoundAction.IGNORE)
	Category category;

	@Column(name="show_on_dashboard")
	Boolean showOnDashboard;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Boolean getShowOnDashboard() {
		return showOnDashboard;
	}

	public void setShowOnDashboard(Boolean showOnDashboard) {
		this.showOnDashboard = showOnDashboard;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public Double getReorderQuantity()
	{
		return reorderQuantity;
	}

	public void setReorderQuantity(Double reorderQuantity)
	{
		this.reorderQuantity = reorderQuantity;
	}

	public Category getCategory()
	{
		return category;
	}

	public void setCategory(Category category)
	{
		this.category = category;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getProductDescription()
	{
		return productDescription;
	}

	public void setProductDescription(String productDescription)
	{
		this.productDescription = productDescription;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getMeasurementUnit()
	{
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit)
	{
		this.measurementUnit = measurementUnit;
	}

}
