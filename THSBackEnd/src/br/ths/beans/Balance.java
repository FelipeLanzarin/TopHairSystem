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
@Table(name="balance")
public class Balance {
	@Id
    @SequenceGenerator(name="SEQ_BALANCE_ID", sequenceName="GEN_BALANCE_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_BALANCE_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private Double value;
	@ManyToOne
	@JoinColumn(name="branch_company_id", referencedColumnName="id")
	private BranchCompany branchCompany;
	@ManyToOne
	@JoinColumn(name="client_id", referencedColumnName="id")
	private Client client;
	
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public BranchCompany getBranchCompany() {
		return branchCompany;
	}
	public void setBranchCompany(BranchCompany branchCompany) {
		this.branchCompany = branchCompany;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
