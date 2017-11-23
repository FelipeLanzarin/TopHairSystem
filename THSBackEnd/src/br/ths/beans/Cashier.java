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
@Table(name="cashier")
public class Cashier {
	@Id
    @SequenceGenerator(name="SEQ_CASHIER_ID", sequenceName="GEN_CASHIER_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_CASHIER_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private Double balance;
	private String description;
	@ManyToOne
	@JoinColumn(name="branch_company_id", referencedColumnName="id")
	private BranchCompany branchCompany;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BranchCompany getBranchCompany() {
		return branchCompany;
	}
	public void setBranchCompany(BranchCompany branchCompany) {
		this.branchCompany = branchCompany;
	}
	
}
