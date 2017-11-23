package br.ths.beans;

import java.util.Date;

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
@Table(name="account")
public class Account {
	@Id
    @SequenceGenerator(name="SEQ_ACCOUNT_ID", sequenceName="GEN_ACCOUNT_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_ACCOUNT_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private Double amount;
	private String description;
	private Date date;
	@Column(name="payment_method")
	private String paymentMethod;
	@ManyToOne
	@JoinColumn(name="branch_company_id", referencedColumnName="id")
	private BranchCompany branchCompany;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public BranchCompany getBranchCompany() {
		return branchCompany;
	}
	public void setBranchCompany(BranchCompany branchCompany) {
		this.branchCompany = branchCompany;
	}
}
