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
@Table(name="transaction")
public class Transaction {
	@Id
    @SequenceGenerator(name="SEQ_TRANSACTION_ID", sequenceName="GEN_TRANSACTION_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_TRANSACTION_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private Double amount;
	@Column(length=9999)
	private String description;
	private Date time;
	private String type;
	@ManyToOne
	@JoinColumn(name="cashier_id", referencedColumnName="id")
	private Cashier cashier;
	@ManyToOne
	@JoinColumn(name="payment_method_id", referencedColumnName="id")
	private PaymentMethod paymentMethod;
	@ManyToOne
	@JoinColumn(name="account_id", referencedColumnName="id")
	private Account account;
	
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Cashier getCashier() {
		return cashier;
	}
	public void setCashier(Cashier cashier) {
		this.cashier = cashier;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	
}
