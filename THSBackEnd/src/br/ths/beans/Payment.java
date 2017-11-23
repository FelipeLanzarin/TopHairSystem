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
@Table(name="payment")
public class Payment {
	@Id
    @SequenceGenerator(name="SEQ_PAYMENT_ID", sequenceName="GEN_PAYMENT_ID", allocationSize=1, initialValue=1000)
    @GeneratedValue(generator="SEQ_PAYMENT_ID",strategy= GenerationType.SEQUENCE)
	private Integer id;
	private Double amount;
	@Column(name="amount_received")
	private Double amountReceived;
	@Column(name="creation_date")
	private Date creationDate;
	private String description;
	@Column(name="last_payment_date")
	private Date lastPaymentDate;
	@ManyToOne
	@JoinColumn(name="order_id", referencedColumnName="id")
	private Order order;
	
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
	public Double getAmountReceived() {
		return amountReceived;
	}
	public void setAmountReceived(Double amountReceived) {
		this.amountReceived = amountReceived;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}
	public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
