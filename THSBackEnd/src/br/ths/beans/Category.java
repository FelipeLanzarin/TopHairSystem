package br.ths.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class Category {
	@Id
    @SequenceGenerator(name="SEQ_CATEGORY_ID", sequenceName="GEN_CATEGORY_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_CATEGORY_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private String name;
	@Column(length=9999)
	private String description;
	@ManyToOne
	@JoinColumn(name="company_id", referencedColumnName="id")
	private Company company;
	
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	@Override
	public String toString() {
		String returName = "";
		if(name != null){
			returName = name;
		}
		return returName;
	}
	
}
