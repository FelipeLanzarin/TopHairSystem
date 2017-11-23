package br.ths.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="city")
public class City {
	
	@Id
    @SequenceGenerator(name="SEQ_CITY_ID", sequenceName="GEN_CITY_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_CITY_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private String name;
	private String uf;
	private String country;

	
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
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}	
}
