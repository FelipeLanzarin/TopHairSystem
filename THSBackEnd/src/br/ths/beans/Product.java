package br.ths.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {
	
	@Id
	private Integer id;//sera criato um gerador proprio
	private String name;
	@Column(length=9999)
	private String description;
	private String type;
	private Integer un;
	private String unType;
	@Column(name="creation_date")
	private Date creationDate;
	private Double price;
	@Column(name="cost_price")
	private Double costPrice;
	@ManyToOne
	@JoinColumn(name="sub_category_id", referencedColumnName="id")
	private SubCategory subCategory;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getUn() {
		return un;
	}
	public void setUn(Integer un) {
		this.un = un;
	}
	public String getUnType() {
		return unType;
	}
	public void setUnType(String unType) {
		this.unType = unType;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	public SubCategory getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}
	
}
