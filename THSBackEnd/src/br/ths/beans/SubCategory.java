package br.ths.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="sub_category")
public class SubCategory {
	@Id
    @SequenceGenerator(name="SEQ", sequenceName="GEN_SUB_CATEGORY_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private String name;
	private String description;
	@ManyToOne
	@JoinColumn(name="category_id", referencedColumnName="id")
	private Category category;
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
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
